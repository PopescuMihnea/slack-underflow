package com.slackunderflow.slackunderflow.mappers;


import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto fromEntityToResponseDto(UserEntity userEntity) {
        return UserResponseDto.builder()
                .email(userEntity.getEmail())
                .badge(userEntity.getBadge())
                .points(userEntity.getPoints()).build();
    }

    public UserEntity fromDtoToEntity(UserDto userDto) {
        return UserEntity.builder()
                .badge(userDto.getBadge())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .points(userDto.getPoints())
                .build();

    }
}
