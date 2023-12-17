package com.slackunderflow.slackunderflow.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BodyRequest {

    @NotEmpty(message = "Must have a body")
    @Size(max = 2000, message = "The body is too long")
    private String body;
}
