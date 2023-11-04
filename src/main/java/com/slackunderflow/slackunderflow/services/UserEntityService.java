package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;

public interface UserEntityService {
    UserResponseDto register(UserDto userDto);

    UserResponseDto login(UserLoginDto userLoginDto);
}
