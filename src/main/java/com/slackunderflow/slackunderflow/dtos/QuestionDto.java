package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class QuestionDto extends AuthDto {
    private String body;
    private Set<TopicEnum> topics;
}
