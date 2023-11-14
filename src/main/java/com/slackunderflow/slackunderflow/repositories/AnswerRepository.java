package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.Answer;
import com.slackunderflow.slackunderflow.models.Question;
import com.slackunderflow.slackunderflow.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Modifying
    @Query(value = "DELETE FROM Answer a where a.id = ?1")
    int customDeleteById(Long id);

    /*@Modifying
    @Query(value = "UPDATE Answer a SET a.rank = ?1 WHERE a.id = ?2")
    void setAnswerRank(Long id, Integer rank);*/

    List<Answer> findByUser(UserEntity user);

    List<Answer> findByQuestion(Question question);

    Answer findFirstByRankAndQuestion(Integer rank, Question question);

    @Modifying
    @Query("UPDATE Answer a " +
            "SET a.rank = CASE " +
            "    WHEN a.rank BETWEEN 1 AND 2 THEN a.rank + 1 " +
            "    WHEN a.rank = 3 THEN 0 " +
            "    ELSE a.rank " +
            "END " +
            "WHERE a.question.id = :questionId AND a.rank >= :insertingRank")
    void incrementRanks(@Param("insertingRank") int insertingRank, @Param("questionId") Long questionId);

    @Modifying
    @Query("UPDATE Answer a " +
            "SET a.rank = CASE " +
            "    WHEN a.rank > 0 AND a.rank <= 3 AND a.rank > :removingRank THEN a.rank - 1 " +
            "    ELSE a.rank " +
            "END " +
            "WHERE a.question.id = :questionId")
    void decrementRanks(@Param("removingRank") int removingRank, @Param("questionId") Long questionId);

    @Query("SELECT COALESCE(MAX(a.rank), 0) FROM Answer a WHERE a.question.id =  ?1")
    Integer findMaxRankByQuestion(Long questionId);

    /*@Modifying
    @Query("UPDATE Answer a SET a.rank = 0 WHERE a.question.id = ?1")
    void setAllRanksZero(Long questionId);*/
}
