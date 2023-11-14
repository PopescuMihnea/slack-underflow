package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.AnswerDto;
import com.slackunderflow.slackunderflow.dtos.AnswerResponseDto;
import com.slackunderflow.slackunderflow.dtos.SuggestionDto;
import com.slackunderflow.slackunderflow.dtos.SuggestionResponseDto;
import com.slackunderflow.slackunderflow.models.Answer;

import java.util.List;

public interface SuggestionService {
    List<SuggestionResponseDto> getAll();

    List<SuggestionResponseDto> getAllByUser(String username);

    List<SuggestionResponseDto> getAllByUser(Long id);

    List<SuggestionResponseDto> getAllByAnswer(Long id);

    SuggestionResponseDto get(Long id);

    SuggestionResponseDto create(SuggestionDto suggestionDto, String username);

    SuggestionResponseDto modify(Long id, SuggestionDto suggestionDto, String username);

    boolean delete(Long id, String username);

    boolean deleteByAnswer(Answer answer);
}
