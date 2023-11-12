package com.slackunderflow.slackunderflow.services;

import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.dtos.QuestionResponseDto;

import java.util.ArrayList;
import java.util.List;

public interface QuestionService {

    List<QuestionResponseDto> getAll();

    List<QuestionResponseDto> getAllByUser(String username);

    List<QuestionResponseDto> getAllByUser(Long id);

    QuestionResponseDto get(Long id);

    QuestionResponseDto create(QuestionDto questionDto, String username);

    QuestionResponseDto modify(Long id, QuestionDto questionDto, String username);

    boolean delete(Long id, String username);
}
