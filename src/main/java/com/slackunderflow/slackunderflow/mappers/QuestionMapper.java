package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.Topic;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.TopicRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final UserEntityRepository userEntityRepository;
    private final TopicRepository topicRepository;

    public Question fromDtoToEntity(QuestionDto questionDto) {
        UserEntity userEntity = userEntityRepository.findByEmail(questionDto.getUserLoginDto().getEmail())
                .orElseThrow(() -> new UserNotFoundError("User not found", questionDto.getUserLoginDto().getEmail()));

        List<Topic> topicList = topicRepository.findAllById(questionDto.getTopics());
        Set<Topic> topicSet = new HashSet<>(topicList);
        return Question.builder()
                .user(userEntity)
                .body(questionDto.getBody())
                .topics(topicSet)
                .build();
    }

}
