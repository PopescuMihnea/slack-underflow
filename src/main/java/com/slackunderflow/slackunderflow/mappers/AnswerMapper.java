package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.AnswerDto;
import com.slackunderflow.slackunderflow.dtos.AnswerResponseDto;
import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.errors.QuestionNotFoundError;
import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.QuestionRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    private QuestionRepository questionRepository;

    public AnswerResponseDto fromEntityToDto(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .body(answer.getBody())
                .rank(answer.getRank())
                .timestamp(answer.getTimestamp())
                .question(answer.getQuestion())
                .user(answer.getUser()).build();
    }

    public Answer fromDtoToEntity(AnswerDto answerDto, UserEntity user) {
        var question = questionRepository.findById(answerDto
                        .getQuestionId())
                .orElseThrow(() ->
                        new QuestionNotFoundError("Question not found with id: ", answerDto.getQuestionId().toString()));

        return Answer.builder().body(answerDto.getBody()).question(question).user(user).build();
    }
}
