package com.slackunderflow.slackunderflow.advices;

import com.slackunderflow.slackunderflow.errors.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ModelAdvice {
    @ExceptionHandler({ModelNotFoundError.class, TopicNotFoundError.class})
    @ApiResponse(responseCode = "404", description = "Model not found")
    public ResponseEntity<Map<String, String>> modelNotFound(ModelNotFoundError error) {
        Map<String, String> map = new HashMap<>();
        map.put("message", error.getMessage());
        map.put("body", error.getBody());

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
