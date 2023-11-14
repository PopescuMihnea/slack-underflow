package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.AnswerDto;
import com.slackunderflow.slackunderflow.dtos.AnswerResponseDto;
import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.dtos.QuestionResponseDto;
import com.slackunderflow.slackunderflow.models.Question;

import java.util.List;

public interface AnswerService {
    List<AnswerResponseDto> getAll();

    List<AnswerResponseDto> resetRanksByQuestion(Long questionId);

    List<AnswerResponseDto> updateRank(Long id, Integer rank, String username);

    List<AnswerResponseDto> getAllByUser(String username);

    List<AnswerResponseDto> getAllByUser(Long id);

    List<AnswerResponseDto> getAllByQuestion(Long id);

    AnswerResponseDto get(Long id);

    AnswerResponseDto create(AnswerDto answerDto, String username);

    AnswerResponseDto modify(Long id, AnswerDto answerDto, String username);

    boolean delete(Long id, String username);

    boolean deleteByQuestion(Question question);
}
