package com.slackunderflow.slackunderflow.controllers;


import com.slackunderflow.slackunderflow.dtos.QuestionDto;
import com.slackunderflow.slackunderflow.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")

    public String createQuestion(@RequestBody QuestionDto questionDto) {

//        questionService.createQuestion(questionDto);
        return "Question Created";

    }
}
