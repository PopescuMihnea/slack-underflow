package com.slackunderflow.slackunderflow.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AnswerDto {

    @NotEmpty
    @NonNull
    private String body;

    @NonNull
    @Min(0)
    private Long questionId;
}
