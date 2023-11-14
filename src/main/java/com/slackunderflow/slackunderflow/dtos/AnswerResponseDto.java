package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerResponseDto {
    private Long id;
    private String body;
    private Integer rank;
    private LocalDate timestamp;
    private Question question;
    private UserEntity user;
}