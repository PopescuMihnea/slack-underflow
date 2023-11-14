package com.slackunderflow.slackunderflow.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SuggestionDto {
    private String body;
    private Long answerId;
}
