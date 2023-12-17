package com.slackunderflow.slackunderflow.controllers;


import com.slackunderflow.slackunderflow.dtos.requests.QuestionRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.QuestionResponseDto;
import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.services.QuestionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getAllByTopics")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsByTopics(@Valid @RequestBody List<TopicEnum> topics) {
        return new ResponseEntity<>(questionService.getAllByTopics(topics), HttpStatus.OK);
    }

    @GetMapping("/getAllByTitle")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsByTitle(@Valid @RequestBody String title) {
        return new ResponseEntity<>(questionService.getAllByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable @Min(0) long id) {
        return new ResponseEntity<>(questionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByUser(@PathVariable @Min(0) long id) {
        return new ResponseEntity<>(questionService.getAllByUser(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByUser(@PathVariable @NotBlank String username) {
        return new ResponseEntity<>(questionService.getAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionResponseDto> createQuestion(Authentication authentication,
                                                              @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        String name = authentication.getName();

        return new ResponseEntity<>(questionService.create(questionRequestDto, name), HttpStatus.CREATED);

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<QuestionResponseDto> modifyQuestion(Authentication authentication,
                                                              @Valid @RequestBody QuestionRequestDto questionRequestDto,
                                                              @PathVariable @Min(0) long id) {
        String name = authentication.getName();

        return new ResponseEntity<>(questionService.modify(id, questionRequestDto, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(Authentication authentication,
                                                 @PathVariable @Min(0) long id) {
        String name = authentication.getName();

        var result = questionService.delete(id, name);
        return new ResponseEntity<>(
                result ? "Question has been deleted" : "Question deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
