package com.slackunderflow.slackunderflow.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class UserLoginDto {
    private String username;
    private String password;
}
