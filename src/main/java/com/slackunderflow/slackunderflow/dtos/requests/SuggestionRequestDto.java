package com.slackunderflow.slackunderflow.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SuggestionRequestDto extends BodyRequest {

    @NotEmpty(message = "Must have answer id")
    @Min(value = 0, message = "Invalid answer id")
    private Long answerId;
}
