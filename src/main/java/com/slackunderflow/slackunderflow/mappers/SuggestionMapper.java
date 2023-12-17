package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.requests.SuggestionRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.SuggestionResponseDto;
import com.slackunderflow.slackunderflow.errors.ModelNotFoundError;
import com.slackunderflow.slackunderflow.models.Suggestion;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuggestionMapper implements BodyEntityMapper<Suggestion, SuggestionResponseDto, SuggestionRequestDto> {

    private final AnswerRepository answerRepository;

    public SuggestionResponseDto fromEntityToResponse(Suggestion suggestion) {
        var userEntity = suggestion.getUser();
        userEntity.setPassword("hehe :)");

        return SuggestionResponseDto.builder()
                .id(suggestion.getId())
                .body(suggestion.getBody())
                .timestamp(suggestion.getTimestamp())
                .answer(suggestion.getAnswer())
                .user(userEntity).build();
    }

    public Suggestion fromRequestToEntity(SuggestionRequestDto suggestionRequestDto, UserEntity user) {
        var answer = answerRepository
                .findById(suggestionRequestDto.getAnswerId())
                .orElseThrow(() ->
                        new ModelNotFoundError("Answer not found with id: ", suggestionRequestDto.getAnswerId().toString()));

        return Suggestion.builder().body(suggestionRequestDto.getBody()).answer(answer).user(user).build();
    }
}
