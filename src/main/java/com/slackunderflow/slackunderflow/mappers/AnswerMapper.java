package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.requests.AnswerRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.AnswerResponseDto;
import com.slackunderflow.slackunderflow.errors.ModelNotFoundError;
import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper implements BodyEntityMapper<Answer, AnswerResponseDto, AnswerRequestDto> {

    private final QuestionRepository questionRepository;

    public AnswerResponseDto fromEntityToResponse(Answer answer) {
        var userEntity = answer.getUser();
        userEntity.setPassword("hehe :)");

        return AnswerResponseDto.builder()
                .id(answer.getId())
                .body(answer.getBody())
                .rank(answer.getRank())
                .timestamp(answer.getTimestamp())
                .question(answer.getQuestion())
                .user(userEntity).build();
    }

    public Answer fromRequestToEntity(AnswerRequestDto answerRequestDto, UserEntity user) {
        var question = questionRepository.findById(answerRequestDto
                        .getQuestionId())
                .orElseThrow(() ->
                        new ModelNotFoundError("Question not found with id: ", answerRequestDto.getQuestionId().toString()));

        return Answer.builder().body(answerRequestDto.getBody()).question(question).user(user).build();
    }
}
