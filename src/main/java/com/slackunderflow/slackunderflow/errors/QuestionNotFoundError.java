package com.slackunderflow.slackunderflow.errors;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class QuestionNotFoundError extends RuntimeException {
    private final String body;

    public QuestionNotFoundError(String message, String body) {
        super(message);
        this.body = body;
    }
}
