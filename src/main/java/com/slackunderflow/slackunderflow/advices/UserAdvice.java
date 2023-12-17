package com.slackunderflow.slackunderflow.advices;

import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserAdvice {

    @ExceptionHandler(UserNotFoundError.class)
    @ApiResponse(responseCode = "401", description = "User is not authorized")
    public ResponseEntity<Map<String, String>> userNotFound(UserNotFoundError error) {
        Map<String, String> map = new HashMap<>();
        map.put("username", error.getUsername());
        map.put("password", error.getPassword());
        map.put("message", error.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ApiResponse(responseCode = "400", description = "Invalid model data")
    public ResponseEntity<Map<String, String>> integrityViolation(SQLIntegrityConstraintViolationException error) {
        Map<String, String> map = new HashMap<>();
        map.put("message", error.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
