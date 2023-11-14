package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserEntityService {
    UserResponseDto register(UserDto userDto);

    UserResponseDto login(UserDto userLoginDto);

    UserResponseDto modify(UserDto userDto);

    UserResponseDto get(Long id);

    boolean delete(String username);

    UserResponseDto updatePoints(String username, Integer points);
}
