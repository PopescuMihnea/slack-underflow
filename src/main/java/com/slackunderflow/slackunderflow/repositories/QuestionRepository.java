package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Query(value = "DELETE FROM Question q where q.id = ?1")
    int customDeleteById(Long id);

    List<Question> findByUser(UserEntity user);
}
