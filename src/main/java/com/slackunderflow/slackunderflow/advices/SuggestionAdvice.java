package com.slackunderflow.slackunderflow.advices;

import com.slackunderflow.slackunderflow.errors.AnswerNotFoundError;
import com.slackunderflow.slackunderflow.errors.SuggestionNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SuggestionAdvice {
    @ExceptionHandler(SuggestionNotFoundError.class)
    public ResponseEntity<?> suggestionNotFound(SuggestionNotFoundError error) {
        Map<String, String> map = new HashMap<>();
        map.put("message", error.getMessage());
        map.put("body", error.getBody());

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
