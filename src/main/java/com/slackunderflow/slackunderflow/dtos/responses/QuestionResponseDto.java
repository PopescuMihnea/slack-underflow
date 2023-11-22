package com.slackunderflow.slackunderflow.dtos.responses;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class QuestionResponseDto extends BodyResponse {
    private Set<TopicEnum> topics;
    private LocalDate timestamp;

}
