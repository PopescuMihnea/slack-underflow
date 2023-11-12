package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TopicDto {
    private TopicEnum topic;

}
