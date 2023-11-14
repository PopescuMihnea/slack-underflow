package com.slackunderflow.slackunderflow.errors;

import lombok.Getter;

@Getter
public class SuggestionNotFoundError extends RuntimeException {
    private final String body;

    public SuggestionNotFoundError(String message, String body) {
        super(message);
        this.body = body;
    }
}

