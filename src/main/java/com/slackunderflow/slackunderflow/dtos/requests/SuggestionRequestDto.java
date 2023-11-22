package com.slackunderflow.slackunderflow.dtos.requests;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SuggestionRequestDto extends BodyRequest {
    private Long answerId;
}
