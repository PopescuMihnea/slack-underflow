package com.slackunderflow.slackunderflow.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerRequestDto extends BodyRequest {

    @NotEmpty(message = "Must have question id")
    @Min(value = 0, message = "Invalid question id")
    private Long questionId;
}
