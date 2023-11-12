package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.TopicDto;
import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.errors.TopicNotFoundError;
import com.slackunderflow.slackunderflow.mappers.TopicMapper;
import com.slackunderflow.slackunderflow.models.Topic;
import com.slackunderflow.slackunderflow.repositories.TopicRepository;
import com.slackunderflow.slackunderflow.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;

    @Override
    public TopicDto get(TopicEnum topicEnum) {
        Topic topic = topicRepository.findByTopic(topicEnum).orElseThrow(() -> new TopicNotFoundError("Topic not found ", topicEnum));

        return topicMapper.fromEntityToDto(topic);
    }
}
