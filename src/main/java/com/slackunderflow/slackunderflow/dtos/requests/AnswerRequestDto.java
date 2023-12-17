package com.slackunderflow.slackunderflow.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerRequestDto extends BodyRequest {

    @Min(value = 0, message = "Invalid question id")
    private Long questionId;
}
