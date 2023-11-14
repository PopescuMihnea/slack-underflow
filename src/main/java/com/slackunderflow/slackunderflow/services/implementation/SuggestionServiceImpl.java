package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.SuggestionDto;
import com.slackunderflow.slackunderflow.dtos.SuggestionResponseDto;
import com.slackunderflow.slackunderflow.errors.AnswerNotFoundError;
import com.slackunderflow.slackunderflow.errors.SuggestionNotFoundError;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.mappers.SuggestionMapper;
import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.AnswerRepository;
import com.slackunderflow.slackunderflow.repositories.SuggestionRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.SuggestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private SuggestionRepository suggestionRepository;
    private SuggestionMapper suggestionMapper;
    private UserEntityRepository userEntityRepository;
    private AnswerRepository answerRepository;

    @Override
    public List<SuggestionResponseDto> getAll() {
        return suggestionRepository
                .findAll()
                .stream()
                .map(suggestion ->
                        suggestionMapper
                                .fromEntityToDto(suggestion))
                .collect(Collectors.toList());
    }

    @Override
    public List<SuggestionResponseDto> getAllByUser(String username) {
        var user = userEntityRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundError("User not found with username: ", username));

        return suggestionRepository.findByUser(user)
                .stream()
                .map(suggestionMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SuggestionResponseDto> getAllByUser(Long id) {
        var user = userEntityRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundError("User not found with id: ", id.toString()));

        return suggestionRepository.findByUser(user)
                .stream()
                .map(suggestionMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SuggestionResponseDto> getAllByAnswer(Long id) {
        var answer = answerRepository
                .findById(id)
                .orElseThrow(() ->
                        new AnswerNotFoundError("Answer not found with id: ", id.toString()));

        return suggestionRepository.findByAnswer(answer)
                .stream()
                .map(suggestionMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SuggestionResponseDto get(Long id) {
        var suggestion = suggestionRepository
                .findById(id)
                .orElseThrow(() ->
                        new SuggestionNotFoundError("Suggestion not found with id: ", id.toString()));
        return suggestionMapper.fromEntityToDto(suggestion);
    }

    @Override
    public SuggestionResponseDto create(SuggestionDto suggestionDto, String username) {
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundError("User not found", username));

        var suggestion = suggestionMapper.fromDtoToEntity(suggestionDto, user);

        suggestionRepository.save(suggestion);

        return suggestionMapper.fromEntityToDto(suggestion);
    }

    @Override
    public SuggestionResponseDto modify(Long id, SuggestionDto suggestionDto, String username) {
        var suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundError("Suggestion not found with id: ", id.toString()));
        var user = suggestion.getUser();

        if (!user.getUsername().equals(username)) {
            throw new SuggestionNotFoundError("Suggestion not found with id: ", id.toString());
        }

        suggestion.setBody(suggestionDto.getBody());
        suggestionRepository.save(suggestion);

        return suggestionMapper.fromEntityToDto(suggestion);
    }

    @Override
    @Transactional
    public boolean delete(Long id, String username) {
        var suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundError("Suggestion not found with id: ", id.toString()));
        var user = suggestion.getUser();

        if (!user.getUsername().equals(username)) {
            throw new SuggestionNotFoundError("Suggestion not found with id: ", id.toString());
        }

        return suggestionRepository.customDeleteById(id) == 1;
    }

    @Override
    public boolean deleteByAnswer(Answer answer) {
        var suggestions = suggestionRepository.findByAnswer(answer);
        AtomicReference<Integer> numberDeleted = new AtomicReference<>(0);
        suggestions.forEach(suggestion -> numberDeleted.updateAndGet(v -> v + suggestionRepository.customDeleteById(suggestion.getId())));

        return numberDeleted.get() == suggestions.size();
    }

    protected void delete(Long id) {
        suggestionRepository.customDeleteById(id);
    }
}
