package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
