package com.slackunderflow.slackunderflow.errors;

import lombok.Getter;

@Getter
public class AnswerNotFoundError extends RuntimeException {
    private final String body;

    public AnswerNotFoundError(String message, String body) {
        super(message);
        this.body = body;
    }
}
