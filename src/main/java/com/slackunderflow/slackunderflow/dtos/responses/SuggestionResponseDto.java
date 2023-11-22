package com.slackunderflow.slackunderflow.dtos.responses;

import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class SuggestionResponseDto extends BodyResponse {

    private Answer answer;
}
