package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.TopicDto;
import com.slackunderflow.slackunderflow.enums.TopicEnum;

public interface TopicService {
    TopicDto get(TopicEnum topicEnum);
}
