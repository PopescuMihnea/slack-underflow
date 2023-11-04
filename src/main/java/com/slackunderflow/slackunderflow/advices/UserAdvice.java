package com.slackunderflow.slackunderflow.advices;

import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserAdvice {

    @ExceptionHandler(UserNotFoundError.class)
    public ResponseEntity<?> userNotFound(UserNotFoundError error) {
        Map<String, String> map = new HashMap<>();
        map.put("email", error.getEmail());
        map.put("password", error.getPassword());
        map.put("message", error.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> violata(SQLIntegrityConstraintViolationException error) {
        Map<String, String> map = new HashMap<>();
        map.put("message", error.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
