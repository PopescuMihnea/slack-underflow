package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.mappers.UserMapper;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(UserDto userDto) {

        UserEntity savedUser = userEntityRepository.save(userMapper.fromDtoToEntity(userDto));

        return userMapper.fromEntityToResponseDto(savedUser);
    }

    public UserResponseDto login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();

        UserEntity savedUser = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundError("User does not exists ", email, password));

        if (!passwordEncoder.matches(password, savedUser.getPassword())) {
            throw new UserNotFoundError("User does not exists ", email, password);
        }


        return userMapper.fromEntityToResponseDto(savedUser);

    }


}
