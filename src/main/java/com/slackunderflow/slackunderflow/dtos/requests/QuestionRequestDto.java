package com.slackunderflow.slackunderflow.dtos.requests;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class QuestionRequestDto extends BodyRequest {
    private Set<TopicEnum> topics;
}
