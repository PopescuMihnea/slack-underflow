package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class QuestionDto {
    private String body;
    private Set<TopicEnum> topics;
}
