package com.slackunderflow.slackunderflow.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AnswerDto {
    private String body;
    private Long questionId;
}
