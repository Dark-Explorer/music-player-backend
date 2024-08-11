package com.darkexplorer.music_player.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    String name;
    @Size(min = 6, message = "USERNAME INVALID")
    String username;
    @Size(min = 6, message = "PASSWORD INVALID")
    String password;
    @Email
    String email;
    String role;
}
