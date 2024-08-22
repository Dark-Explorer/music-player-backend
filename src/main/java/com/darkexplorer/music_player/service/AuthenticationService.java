package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.AuthenticationRequest;
import com.darkexplorer.music_player.dto.request.IntrospectRequest;
import com.darkexplorer.music_player.dto.request.LogoutRequest;
import com.darkexplorer.music_player.dto.response.AuthenticationResponse;
import com.darkexplorer.music_player.dto.response.IntrospectResponse;
import com.darkexplorer.music_player.entity.InvalidatedToken;
import com.darkexplorer.music_player.entity.User;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
import com.darkexplorer.music_player.repository.IInvalidatedTokenRepo;
import com.darkexplorer.music_player.repository.IUserRepo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    IUserRepo userRepo;
    IInvalidatedTokenRepo invalidatedTokenRepo;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    // Thời gian 1 token còn hiệu lực
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    // Sau khi token hết hạn 1 khoảng thời gian vẫn refresh được
    protected long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        // Kiểm tra tính hợp lệ của token, tương tự với việc dùng jwt.io để decode token
        // Token được parse ra để so sánh các dữ liệu về key và expiryTime
        var token = request.getToken();

        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepo.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()   // Nếu verify để refresh token
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();                  // Nếu verify để

        var verified = signedJWT.verify(verifier);  // Trả về true/false kết quả so khớp token

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(User user) {
        // 1 JWT gồm header, payload, signature

        // Header chứa thuật toán mã hóa JWT
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Payload gồm các thông tin cần sử dụng đến trong phiên làm việc (vd tên đăng nhập, thời gian hết hạn token,...)
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("darkexplorer.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))   // Hết hạn sau ...
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        // Signature: hash của 2 phần trên kèm theo signer key
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        return user.getRole().toString();
    }
}
