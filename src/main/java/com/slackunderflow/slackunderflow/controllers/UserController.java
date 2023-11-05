package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.UserLoginDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserDto userDto) {
        UserResponseDto userResponseDto = userEntityService.register(userDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                userEntityService.login(userDto),
                HttpStatus.OK
        );
    }

}
