package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.responses.UserResponseDto;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserEntityService userEntityService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userEntityService.get(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserDto userDto) {
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

    @PutMapping("/modify")
    public ResponseEntity<UserResponseDto> modify(Authentication authentication, @RequestBody UserDto userDto) {
        String name = authentication.getName();

        if (Objects.equals(name, userDto.getUsername())) {
            return new ResponseEntity<>(
                    userEntityService.modify(userDto),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(Authentication authentication) {
        String name = authentication.getName();
        boolean result = userEntityService.delete(name);
        return new ResponseEntity<>(
                result ? "User has been deleted" : "User deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

}
