package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.SuggestionDto;
import com.slackunderflow.slackunderflow.dtos.SuggestionResponseDto;
import com.slackunderflow.slackunderflow.errors.AnswerNotFoundError;
import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.Suggestion;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuggestionMapper {

    private AnswerRepository answerRepository;

    public SuggestionResponseDto fromEntityToDto(Suggestion suggestion) {
        return SuggestionResponseDto.builder()
                .id(suggestion.getId())
                .body(suggestion.getBody())
                .timestamp(suggestion.getTimestamp())
                .answer(suggestion.getAnswer())
                .user(suggestion.getUser()).build();
    }

    public Suggestion fromDtoToEntity(SuggestionDto suggestionDto, UserEntity user) {
        var answer = answerRepository
                .findById(suggestionDto.getAnswerId())
                .orElseThrow(() ->
                        new AnswerNotFoundError("Answer not found with id: ", suggestionDto.getAnswerId().toString()));

        return Suggestion.builder().body(suggestionDto.getBody()).answer(answer).user(user).build();
    }
}
