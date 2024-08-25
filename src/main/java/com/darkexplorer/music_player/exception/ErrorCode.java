package com.darkexplorer.music_player.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    ARTIST_NOT_FOUND(700, "Artist not found!", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(701, "Username already in used!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(702, "User not found!", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(703, "Email already in used!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(704, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(705, "You don't have permission", HttpStatus.FORBIDDEN),
    SONG_NOT_FOUND(706, "Song not found!", HttpStatus.NOT_FOUND),
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
