package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.requests.QuestionRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.QuestionResponseDto;
import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.errors.TopicNotFoundError;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.Topic;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.TopicRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper implements BodyEntityMapper<Question, QuestionResponseDto, QuestionRequestDto> {

    private final UserEntityRepository userEntityRepository;
    private final TopicRepository topicRepository;

    public QuestionResponseDto fromEntityToResponse(Question question) {
        Set<Topic> topics = question.getTopics();
        Set<TopicEnum> topicEnums = topics.stream()
                .map(Topic::getTopic)
                .collect(Collectors.toSet());

        var userEntity = question.getUser();
        userEntity.setPassword("hehe :)");


        return QuestionResponseDto.builder().
                id(question.getId()).
                title(question.getTitle()).
                topics(topicEnums).
                body(question.getBody()).
                timestamp(question.getTimestamp()).
                user(userEntity).build();
    }

    public Question fromRequestToEntity(QuestionRequestDto questionRequestDto, UserEntity user) {
        Set<TopicEnum> topicEnums = questionRequestDto.getTopics();
        Set<Topic> topics = topicEnums
                .stream()
                .map(topic -> topicRepository.findByTopic(topic).orElseThrow(() -> new TopicNotFoundError("Topic not found", topic)))
                .collect(Collectors.toSet());

        return Question.builder()
                .title(questionRequestDto.getTitle())
                .body(questionRequestDto.getBody())
                .timestamp(LocalDate.now())
                .topics(topics)
                .user(user).build();
    }

}
