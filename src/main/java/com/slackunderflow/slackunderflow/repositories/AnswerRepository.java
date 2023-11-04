package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
