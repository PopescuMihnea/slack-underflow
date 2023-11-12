package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
