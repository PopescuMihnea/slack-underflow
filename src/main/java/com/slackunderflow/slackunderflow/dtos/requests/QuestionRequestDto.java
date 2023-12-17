package com.slackunderflow.slackunderflow.dtos.requests;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class QuestionRequestDto extends BodyRequest {

    @NotEmpty(message = "Must have a title")
    @Size(min = 5, message = "Enter a more descriptive(longer) title")
    @Size(max = 100, message = "The title is too long")
    private String title;

    @NotEmpty(message = "Question must have topics")
    private Set<TopicEnum> topics;
}
