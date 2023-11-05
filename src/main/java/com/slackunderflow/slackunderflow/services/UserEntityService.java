package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserEntityService {
    UserResponseDto register(UserDto userDto);

    UserResponseDto login(UserDto userLoginDto);
}
