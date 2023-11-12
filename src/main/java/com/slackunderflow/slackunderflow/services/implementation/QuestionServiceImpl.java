package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.dtos.QuestionResponseDto;
import com.slackunderflow.slackunderflow.errors.QuestionNotFoundError;
import com.slackunderflow.slackunderflow.errors.TopicNotFoundError;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.mappers.QuestionMapper;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.QuestionRepository;
import com.slackunderflow.slackunderflow.repositories.TopicRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserEntityRepository userEntityRepository;
    private final TopicRepository topicRepository;

    @Override
    public List<QuestionResponseDto> getAll() {
        var list = questionRepository.findAll()
                .stream()
                .map(questionMapper::fromEntityToDto)
                .toList();

        return new ArrayList<>(list);
    }

    @Override
    public List<QuestionResponseDto> getAllByUser(String username) {
        var user = userEntityRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundError("User not found with username", username));

        return questionRepository.findByUser(user)
                .stream()
                .map(questionMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public List<QuestionResponseDto> getAllByUser(Long id) {
        var user = userEntityRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundError("User not found with id", id.toString()));

        return questionRepository.findByUser(user)
                .stream()
                .map(questionMapper::fromEntityToDto)
                .toList();
    }


    @Override
    public QuestionResponseDto get(Long id) {
        var question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundError("Question not found with id: ", id.toString()));
        return questionMapper.fromEntityToDto(question);
    }

    @Override
    public QuestionResponseDto create(QuestionDto questionDto, String username) {
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundError("User not found", username));
        Question question = questionMapper.fromDtoToEntity(questionDto, user);
        questionRepository.save(question);

        return questionMapper.fromEntityToDto(question);
    }

    @Override
    public QuestionResponseDto modify(Long id, QuestionDto questionDto, String username) {

        var question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundError("Question not found with id: ", id.toString()));
        var user = question.getUser();

        if (!user.getUsername().equals(username)) {
            throw new QuestionNotFoundError("Question not found with id: ", id.toString());
        }

        question.setBody(questionDto.getBody());
        question.setTopics(
                questionDto.
                        getTopics()
                        .stream()
                        .map(topic ->
                                topicRepository
                                        .findByTopic(topic)
                                        .orElseThrow(() ->
                                                new TopicNotFoundError("Topic not found", topic)))
                        .collect(Collectors.toSet()));

        questionRepository.save(question);

        return questionMapper.fromEntityToDto(question);
    }

    @Override
    @Transactional
    public boolean delete(Long id, String username) {
        var question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundError("Question not found with id: ", id.toString()));
        var user = question.getUser();

        if (!user.getUsername().equals(username)) {
            throw new QuestionNotFoundError("Question not found with id: ", id.toString());
        }

        return questionRepository.customDeleteById(id) == 1;
    }
}
