package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.mappers.UserMapper;
import com.slackunderflow.slackunderflow.models.Role;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.RoleRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.security.TokenService;
import com.slackunderflow.slackunderflow.services.QuestionService;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final QuestionService questionService;
    private static final int MAX_POINTS = 75;

    public UserResponseDto register(UserDto userDto) {

        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        userEntityRepository.save(userMapper.fromDtoToEntity(userDto, roles));

        return authAndCreateToken(userDto);
    }

    public UserResponseDto login(UserDto userDto) {

        return authAndCreateToken(userDto);

    }

    @Override
    public UserResponseDto modify(UserDto userDto) {
        String username = userDto.getUsername();

        var user = userEntityRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new UserNotFoundError("User not found", username));
        user.setPassword(userDto.getPassword());
        userEntityRepository.save(user);

        return userMapper.fromEntityToResponseDto(user, "");
    }

    @Override
    public UserResponseDto get(Long id) {
        var user = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundError("User not found with id: ", id.toString()));

        return userMapper.fromEntityToResponseDto(user, "");
    }

    @Override
    @Transactional
    public boolean delete(String username) {
        var user = userEntityRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundError("User is not found", username));

        if (!questionService.deleteByUser(user)) {
            return false;
        }

        return userEntityRepository.deleteByUsername(username) == 1;
    }


    private UserResponseDto authAndCreateToken(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = tokenService.generateJwt(auth);

        var savedUser = userEntityRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundError("User not found", username));
        return userMapper.fromEntityToResponseDto(savedUser, token);
    }

    public UserResponseDto updatePoints(String username, Integer points) {
        var user = userEntityRepository
                .findByUsername(username).orElseThrow(() -> new UserNotFoundError("User not found with username: ", username));

        var newPoints = user.getPoints() + points;
        user.setPoints(newPoints);

        var badge = getBadgeFromPoints(newPoints);
        user.setBadge(badge);

        userEntityRepository.save(user);

        return userMapper.fromEntityToResponseDto(user, "");
    }

    private BadgeEnum getBadgeFromPoints(Integer points) {
        return switch (Math.min(points, MAX_POINTS) / 25) {
            case 1, 2 -> BadgeEnum.INTERMEDIATE;
            case 3 -> BadgeEnum.BOSS;
            default -> BadgeEnum.SLAVE;
        };
    }


}
