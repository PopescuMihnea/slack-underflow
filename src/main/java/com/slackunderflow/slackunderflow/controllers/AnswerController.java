package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.requests.AnswerRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.AnswerResponseDto;
import com.slackunderflow.slackunderflow.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AnswerResponseDto>> getAllAnswers() {
        return new ResponseEntity<>(answerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AnswerResponseDto> getAnswer(@PathVariable long id) {
        return new ResponseEntity<>(answerService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByUser(@PathVariable long id) {
        return new ResponseEntity<>(answerService.getAllByUser(id), HttpStatus.OK);
    }

    @GetMapping("/get/question/{questionId}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByQuestion(@PathVariable Long questionId) {
        return new ResponseEntity<>(answerService.getAllByQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByUser(@PathVariable String username) {
        return new ResponseEntity<>(answerService.getAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AnswerResponseDto> createAnswer(Authentication authentication, @RequestBody AnswerRequestDto answerRequestDto) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.create(answerRequestDto, name), HttpStatus.CREATED);

    }

    @PatchMapping("/reset/{questionId}")
    public ResponseEntity<List<AnswerResponseDto>> resetRank(@PathVariable Long questionId) {
        return new ResponseEntity<>(answerService.resetRanksByQuestion(questionId), HttpStatus.OK);
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity<AnswerResponseDto> modifyAnswer(Authentication authentication, @RequestBody AnswerRequestDto answerRequestDto, @PathVariable long id) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.modify(id, answerRequestDto, name), HttpStatus.OK);
    }

    @PatchMapping("/modify/rank/{id}")
    public ResponseEntity<List<AnswerResponseDto>> updateRank(Authentication authentication, @PathVariable Long id, @RequestParam Integer rank) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.updateRank(id, rank, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnswer(Authentication authentication, @PathVariable long id) {
        String name = authentication.getName();

        var result = answerService.delete(id, name);
        return new ResponseEntity<>(
                result ? "answer has been deleted" : "answer deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
