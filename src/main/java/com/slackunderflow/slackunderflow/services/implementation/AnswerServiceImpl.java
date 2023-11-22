package com.slackunderflow.slackunderflow.services.implementation;

import com.slackunderflow.slackunderflow.dtos.AnswerDto;
import com.slackunderflow.slackunderflow.dtos.AnswerResponseDto;
import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.dtos.UserResponseDto;
import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import com.slackunderflow.slackunderflow.errors.AnswerNotFoundError;
import com.slackunderflow.slackunderflow.errors.QuestionNotFoundError;
import com.slackunderflow.slackunderflow.errors.UserNotFoundError;
import com.slackunderflow.slackunderflow.mappers.AnswerMapper;
import com.slackunderflow.slackunderflow.mappers.UserMapper;
import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import com.slackunderflow.slackunderflow.repositories.AnswerRepository;
import com.slackunderflow.slackunderflow.repositories.QuestionRepository;
import com.slackunderflow.slackunderflow.repositories.SuggestionRepository;
import com.slackunderflow.slackunderflow.repositories.UserEntityRepository;
import com.slackunderflow.slackunderflow.services.AnswerService;
import com.slackunderflow.slackunderflow.services.SuggestionService;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final UserEntityRepository userEntityRepository;
    private final QuestionRepository questionRepository;
    private final SuggestionService suggestionService;
    private final UserMapper userMapper;
    
    private static final int MAX_POINTS = 75;
    private static final int MAX_RANK = 3;
    private static final int MIN_RANK = 1;

    @Override
    public List<AnswerResponseDto> getAll() {
        return answerRepository.findAll()
                .stream()
                .map(answerMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerResponseDto> resetRanksByQuestion(Long questionId) {
        var question = questionRepository
                .findById(questionId)
                .orElseThrow(() ->
                        new QuestionNotFoundError("Question not found with id: ", questionId.toString()));

        var answers = answerRepository.findByQuestion(question);

        answers.forEach(answer -> {
            modifyAnswerRank(answer, 0);
        });

        return getAll();
    }

    @Override
    public List<AnswerResponseDto> updateRank(Long id, Integer rank, String username) {
        var answer = answerRepository
                .findById(id)
                .orElseThrow(() ->
                        new AnswerNotFoundError("Answer not found with id: ", id.toString()));

        var question = answer.getQuestion();

        var user = userEntityRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundError("User not found with username: ", username));

        if (!question.getUser().getUsername().equals(user.getUsername())) {
            throw new AnswerNotFoundError("Answer not found with id: ", id.toString());
        }

        if (rank <= MAX_RANK) {
            var maxRank = answerRepository.findMaxRankByQuestion(question.getId());

            if (answer.getRank() >= MIN_RANK && answer.getRank() < MAX_RANK && rank == 0) {
                var modifyAnswers = answerRepository.findDecrementRanks(rank, question.getId());

                modifyAnswers.forEach(modifyAnswer -> {
                    modifyAnswerRank(modifyAnswer, modifyAnswer.getRank() - 1);
                });
            } else if (answer.getRank() == 0 && rank <= maxRank) {
                var modifyAnswers = answerRepository.findIncrementRanks(rank, question.getId());

                modifyAnswers.forEach(modifyAnswer -> {
                    modifyAnswerRank(modifyAnswer, modifyAnswer.getRank() + 1);
                });
            } else if (!Objects.equals(answer.getRank(), rank) && answer.getRank() <= MAX_RANK && answer.getRank() >= MIN_RANK && rank <= maxRank) {
                var otherAnswer = answerRepository.findFirstByRankAndQuestion(rank, question);

                if (otherAnswer.getRank() > answer.getRank()) {
                    modifyAnswerRank(otherAnswer, 0);
                } else {
                    modifyAnswerRank(otherAnswer, answer.getRank());
                }

                answerRepository.save(otherAnswer);
            }

            modifyAnswerRank(answer, rank);
        }

        return getAll();
    }

    @Override
    public List<AnswerResponseDto> getAllByUser(String username) {
        var user = userEntityRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundError("User not found with username: ", username));

        return answerRepository.findByUser(user)
                .stream()
                .map(answerMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerResponseDto> getAllByUser(Long id) {
        var user = userEntityRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundError("User not found with id: ", id.toString()));

        return answerRepository.findByUser(user)
                .stream()
                .map(answerMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerResponseDto> getAllByQuestion(Long id) {
        var question = questionRepository
                .findById(id)
                .orElseThrow(() ->
                        new QuestionNotFoundError("Question not found with id: ", id.toString()));

        return answerRepository.findByQuestion(question)
                .stream()
                .map(answerMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnswerResponseDto get(Long id) {
        var answer = answerRepository
                .findById(id)
                .orElseThrow(() ->
                        new AnswerNotFoundError("Answer not found with id: ", id.toString()));
        return answerMapper.fromEntityToDto(answer);
    }

    @Override
    public AnswerResponseDto create(AnswerDto answerDto, String username) {
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundError("User not found", username));

        var answer = answerMapper.fromDtoToEntity(answerDto, user);

        answerRepository.save(answer);

        return answerMapper.fromEntityToDto(answer);
    }

    @Override
    public AnswerResponseDto modify(Long id, AnswerDto answerDto, String username) {
        var answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundError("Answer not found with id: ", id.toString()));
        var user = answer.getUser();

        if (!user.getUsername().equals(username)) {
            throw new AnswerNotFoundError("Answer not found with id: ", id.toString());
        }

        answer.setBody(answerDto.getBody());
        answerRepository.save(answer);

        return answerMapper.fromEntityToDto(answer);
    }

    @Override
    @Transactional
    public boolean delete(Long id, String username) {
        var answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundError("Answer not found with id: ", id.toString()));
        var user = answer.getUser();

        if (!user.getUsername().equals(username)) {
            throw new AnswerNotFoundError("Answer not found with id: ", id.toString());
        }

        if (!suggestionService.deleteByAnswer(answer)) {
            return false;
        }

        var pointChange = -getPointsFromRank(answer.getRank());
        updatePoints(user.getUsername(), pointChange);

        return answerRepository.customDeleteById(id) == 1;
    }

    @Override
    public boolean deleteByQuestion(Question question) {
        var answers = answerRepository.findByQuestion(question);

        AtomicReference<Integer> numberDeleted = new AtomicReference<>(0);
        answers.forEach(answer -> {
            var pointChange = -getPointsFromRank(answer.getRank());
            var user = answer.getUser();

            updatePoints(user.getUsername(), pointChange);

            if (suggestionService.deleteByAnswer(answer)) {
                numberDeleted.updateAndGet(v -> v + answerRepository.customDeleteById(answer.getId()));
            }
        });

        return numberDeleted.get() == answers.size();
    }

    private Integer getPointsFromRank(Integer rank) {
        return switch (rank) {
            case 1 -> 15;
            case 2 -> 10;
            case 3 -> 5;
            default -> 0;
        };
    }

    private void modifyAnswerRank(Answer answer, Integer newRank) {
        if (newRank < MIN_RANK || newRank > MAX_RANK) {
            throw new IllegalArgumentException("The new rank is outside the limits");
        }

        var pointChange = getPointsFromRank(newRank) - getPointsFromRank(answer.getRank());
        var user = answer.getUser();
        updatePoints(user.getUsername(), pointChange);

        answer.setRank(newRank);
        answerRepository.save(answer);
    }

    private UserResponseDto updatePoints(String username, Integer points) {
        var user = userEntityRepository
                .findByUsername(username).orElseThrow(() -> new UserNotFoundError("User not found with username: ", username));

        var newPoints = user.getPoints() + points;
        user.setPoints(newPoints);

        var badge = getBadgeFromPoints(newPoints);
        user.setBadge(badge);

        userEntityRepository.save(user);

        return userMapper.fromEntityToResponseDto(user, "");
    }

    private BadgeEnum getBadgeFromPoints(Integer points) {
        return switch (Math.min(points, MAX_POINTS) / 25) {
            case 1, 2 -> BadgeEnum.INTERMEDIATE;
            case 3 -> BadgeEnum.BOSS;
            default -> BadgeEnum.SLAVE;
        };
    }
}
