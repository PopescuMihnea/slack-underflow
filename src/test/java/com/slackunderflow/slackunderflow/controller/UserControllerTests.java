package com.slackunderflow.slackunderflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.slackunderflow.slackunderflow.configuration.SecurityConfig;
import com.slackunderflow.slackunderflow.configuration.TestSecurityConfig;
import com.slackunderflow.slackunderflow.controllers.UserController;
import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.responses.UserResponseDto;
import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import com.slackunderflow.slackunderflow.models.Role;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.implementation.UserEntityServiceImpl;
import com.slackunderflow.slackunderflow.utils.RsaKeyProperties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserController.class)
@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityServiceImpl userEntityService;

    private final String authority = "USER";
    private Role role;
    private UserEntity user;
    private UserDto userDto;
    private final String username = "Mihnea";
    private final String password = "12345";

    private static ObjectWriter writer;

    @BeforeClass
    public static void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Before
    public void init() {
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
    }

    @DisplayName("JUnit test for register controller")
    @Test
    public void givenUser_whenRegister_thenReturnJson() throws Exception {
        var token = "JWT here";
        var userResponseDto = new UserResponseDto(user.getUsername(), user.getPoints(), user.getBadge(), token);

        given(userEntityService.register(userDto)).willReturn(userResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(userDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.points").value(user.getPoints()))
                .andExpect(jsonPath("$.badge").value(user.getBadge().toString()))
                .andExpect(jsonPath("$.jwt").value(userResponseDto.getJwt()));
    }
}
