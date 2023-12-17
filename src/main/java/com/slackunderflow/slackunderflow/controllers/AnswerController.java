package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.requests.AnswerRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.AnswerResponseDto;
import com.slackunderflow.slackunderflow.services.AnswerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
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
@RequestMapping("/answer")
@RequiredArgsConstructor
@Validated
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AnswerResponseDto>> getAllAnswers() {
        return new ResponseEntity<>(answerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AnswerResponseDto> getAnswer(@PathVariable @Min(0) long id) {
        return new ResponseEntity<>(answerService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByUser(@PathVariable @Min(0) long id) {
        return new ResponseEntity<>(answerService.getAllByUser(id), HttpStatus.OK);
    }

    @GetMapping("/get/question/{questionId}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByQuestion(@PathVariable @Min(0) long questionId) {
        return new ResponseEntity<>(answerService.getAllByQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<List<AnswerResponseDto>> getAnswersByUser(@PathVariable @NotBlank String username) {
        return new ResponseEntity<>(answerService.getAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AnswerResponseDto> createAnswer(Authentication authentication,
                                                          @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.create(answerRequestDto, name), HttpStatus.CREATED);

    }

    @PatchMapping("/reset/{questionId}")
    public ResponseEntity<List<AnswerResponseDto>> resetRank(@PathVariable @Min(0) long questionId) {
        return new ResponseEntity<>(answerService.resetRanksByQuestion(questionId), HttpStatus.OK);
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity<AnswerResponseDto> modifyAnswer(Authentication authentication,
                                                          @Valid @RequestBody AnswerRequestDto answerRequestDto,
                                                          @PathVariable @Min(0) long id) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.modify(id, answerRequestDto, name), HttpStatus.OK);
    }

    @PatchMapping("/modify/rank/{id}")
    public ResponseEntity<List<AnswerResponseDto>> updateRank(Authentication authentication,
                                                              @PathVariable @Min(0) long id,
                                                              @RequestParam @Min(1) @Max(3) Integer rank) {
        String name = authentication.getName();

        return new ResponseEntity<>(answerService.updateRank(id, rank, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnswer(Authentication authentication,
                                               @PathVariable @Min(0) long id) {
        String name = authentication.getName();

        var result = answerService.delete(id, name);
        return new ResponseEntity<>(
                result ? "answer has been deleted" : "answer deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
