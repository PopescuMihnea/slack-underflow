package com.slackunderflow.slackunderflow.service;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.responses.UserResponseDto;
import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import com.slackunderflow.slackunderflow.mappers.UserMapper;
import com.slackunderflow.slackunderflow.models.Role;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.RoleRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.security.TokenService;
import com.slackunderflow.slackunderflow.services.QuestionService;
import com.slackunderflow.slackunderflow.services.implementation.UserEntityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserMapper userMapper;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private UserEntityServiceImpl userEntityService;

    private final String authority = "USER";
    private Role role;
    private UserEntity user;
    private UserDto userDto;
    private final String username = "Mihnea";
    private final String password = "12345";

    @BeforeEach
    public void setup() {
        role = new Role(1L, authority);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        userDto = new UserDto(username, password);

        user = UserEntity
                .builder()
                .id(1L)
                .badge(BadgeEnum.BEGINNER)
                .password("Hashed password")
                .points(0)
                .username(username)
                .authorities(roles).build();

        given(roleRepository.findByAuthority(authority))
                .willReturn(Optional.of(role));
        given(userMapper.fromDtoToEntity(userDto, roles))
                .willReturn(user);
    }

    @DisplayName("JUnit test for register service")
    @Test
    public void givenUserEntityObject_whenRegisterUserEntity_thenReturnUserResponseDto() {
        var auth = Mockito.mock(Authentication.class);

        given(authenticationManager.authenticate(Mockito.any()))
                .willReturn(auth);

        var token = "JWT here";

        UserResponseDto userResponseDto = new UserResponseDto(user.getUsername(), user.getPoints(), user.getBadge(), token);

        given(tokenService.generateJwt(auth)).willReturn(token);
        given(userEntityRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        given(userMapper.fromEntityToResponseDto(user, token)).willReturn(userResponseDto);

        var createdUser = userEntityService.register(userDto);
        System.out.println("Test1 UserService result: " + createdUser.toString());

        assertThat(createdUser).isEqualTo(userResponseDto);
    }


}
