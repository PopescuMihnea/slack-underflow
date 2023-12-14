package com.slackunderflow.slackunderflow.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerRequestDto extends BodyRequest {
    @NonNull
    @Min(0)
    private Long questionId;
}