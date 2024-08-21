package com.darkexplorer.music_player.mapper;

import com.darkexplorer.music_player.dto.request.UserSignUpRequest;
import com.darkexplorer.music_player.dto.response.UserResponse;
import com.darkexplorer.music_player.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserSignUpRequest userSignUpRequest);
    UserResponse toUserResponse(User user);
}
