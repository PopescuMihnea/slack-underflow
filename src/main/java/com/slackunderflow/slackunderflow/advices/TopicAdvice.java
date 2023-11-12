package com.slackunderflow.slackunderflow.advices;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.errors.TopicNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class TopicAdvice {

    @ExceptionHandler(TopicNotFoundError.class)
    public ResponseEntity<?> topicNotFound(TopicNotFoundError error) {
        Map<String, String> map = new HashMap<>();
        map.put("message", error.getMessage());
        map.put("body", error.getBody());


        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
