package com.slackunderflow.slackunderflow.controllers;


import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.dtos.QuestionResponseDto;
import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.models.Topic;
import com.slackunderflow.slackunderflow.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getAllByTopics")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsByTopics(@RequestBody List<TopicEnum> topics) {
        return new ResponseEntity<>(questionService.getAllByTopics(topics), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable long id) {
        return new ResponseEntity<>(questionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByUser(@PathVariable long id) {
        return new ResponseEntity<>(questionService.getAllByUser(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByUser(@PathVariable String username) {
        return new ResponseEntity<>(questionService.getAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionResponseDto> createQuestion(Authentication authentication, @RequestBody QuestionDto questionDto) {
        String name = authentication.getName();

        return new ResponseEntity<>(questionService.create(questionDto, name), HttpStatus.CREATED);

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<QuestionResponseDto> modifyQuestion(Authentication authentication, @RequestBody QuestionDto questionDto, @PathVariable long id) {
        String name = authentication.getName();

        return new ResponseEntity<>(questionService.modify(id, questionDto, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(Authentication authentication, @PathVariable long id) {
        String name = authentication.getName();

        var result = questionService.delete(id, name);
        return new ResponseEntity<>(
                result ? "Question has been deleted" : "Question deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
