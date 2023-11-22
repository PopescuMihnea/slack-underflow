package com.slackunderflow.slackunderflow.dtos.responses;

import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BodyResponse {
    private Long id;
    private String body;
    private LocalDate timestamp;
    private UserEntity user;
}
