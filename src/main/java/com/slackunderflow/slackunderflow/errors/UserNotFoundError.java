package com.slackunderflow.slackunderflow.errors;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserNotFoundError extends RuntimeException {
    private String email;
    private String password = "";

    public UserNotFoundError(String message, String email, String password) {
        super(message);
        this.email = email;
        this.password = password;
    }

    public UserNotFoundError(String message, String email) {
        super(message);
        this.email = email;

    }


}
