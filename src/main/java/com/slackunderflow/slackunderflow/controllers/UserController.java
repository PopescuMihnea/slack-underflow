package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.UserDto;
import com.slackunderflow.slackunderflow.dtos.responses.UserResponseDto;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserEntityService userEntityService;


    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable @Min(0) long id) {
        return new ResponseEntity<>(userEntityService.get(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Validated(UserDto.RegisteringUser.class) @RequestBody UserDto userDto) {
        UserResponseDto userResponseDto = userEntityService.register(userDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                userEntityService.login(userDto),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Modifies the logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modified the user with the date provided",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PutMapping("/modify")
    public ResponseEntity<UserResponseDto> modify(Authentication authentication,
                                                  @io.swagger.v3.oas.annotations.parameters.
                                                          RequestBody(description = "The data of the user that is to be modified", required = true)
                                                  @Valid @RequestBody UserDto userDto) {
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
