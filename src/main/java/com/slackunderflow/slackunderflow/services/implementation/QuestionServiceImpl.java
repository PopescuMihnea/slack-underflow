package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.mappers.QuestionMapper;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.repositories.QuestionRepository;
import com.slackunderflow.slackunderflow.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public Boolean createQuestion(QuestionDto questionDto) {
        Question question = questionMapper.fromDtoToEntity(questionDto);
        questionRepository.save(question);

        return true;
    }
}
