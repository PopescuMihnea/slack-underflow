package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.models.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionResponseDto {
    private Long id;
    private String body;
    private Set<TopicEnum> topics;
    private LocalDate timestamp;
    private UserEntity user;
}
