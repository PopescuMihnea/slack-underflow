package com.slackunderflow.slackunderflow.controllers;

import com.slackunderflow.slackunderflow.dtos.requests.SuggestionRequestDto;
import com.slackunderflow.slackunderflow.dtos.responses.SuggestionResponseDto;
import com.slackunderflow.slackunderflow.services.SuggestionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
@Validated
public class SuggestionController {
    private final SuggestionService suggestionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<SuggestionResponseDto>> getAllSuggestions() {
        return new ResponseEntity<>(suggestionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SuggestionResponseDto> getSuggestion(@PathVariable @NotBlank @Min(0) long id) {
        return new ResponseEntity<>(suggestionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsByUser(@PathVariable @NotBlank @Min(0) long id) {
        return new ResponseEntity<>(suggestionService.getAllByUser(id), HttpStatus.OK);
    }

    @GetMapping("/get/question/{answerId}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsByAnswer(@PathVariable @NotBlank @Min(0) long answerId) {
        return new ResponseEntity<>(suggestionService.getAllByAnswer(answerId), HttpStatus.OK);
    }

    @GetMapping("/get/user/{username}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsByUser(@PathVariable @NotBlank String username) {
        return new ResponseEntity<>(suggestionService.getAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SuggestionResponseDto> createSuggestion(Authentication authentication,
                                                                  @RequestBody SuggestionRequestDto suggestionRequestDto) {
        String name = authentication.getName();

        return new ResponseEntity<>(suggestionService.create(suggestionRequestDto, name), HttpStatus.CREATED);

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<SuggestionResponseDto> modifySuggestion(Authentication authentication,
                                                                  @RequestBody SuggestionRequestDto suggestionRequestDto,
                                                                  @PathVariable @NotBlank @Min(0) long id) {
        String name = authentication.getName();

        return new ResponseEntity<>(suggestionService.modify(id, suggestionRequestDto, name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSuggestion(Authentication authentication,
                                                   @PathVariable @NotBlank @Min(0) long id) {
        String name = authentication.getName();

        var result = suggestionService.delete(id, name);
        return new ResponseEntity<>(
                result ? "suggestion has been deleted" : "suggestion deletion failed",
                result ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }
}
