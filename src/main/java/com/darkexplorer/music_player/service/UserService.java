package com.darkexplorer.music_player.service;

import com.darkexplorer.music_player.dto.request.UserSignUpRequest;
import com.darkexplorer.music_player.dto.request.UserUpdateRequest;
import com.darkexplorer.music_player.dto.response.UserResponse;
import com.darkexplorer.music_player.entity.User;
import com.darkexplorer.music_player.enums.Role;
import com.darkexplorer.music_player.exception.AppException;
import com.darkexplorer.music_player.exception.ErrorCode;
import com.darkexplorer.music_player.mapper.UserMapper;
import com.darkexplorer.music_player.repository.IUserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserRepo userRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse signUp(UserSignUpRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user = userRepo.save(user);
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse editInfo(String userId, UserUpdateRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user = userRepo.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }
}
