package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.dto.TopicInfo;
import com.dev.willen.eduplanner.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    List<Exercise> findAllByUserId(int userId);

    @Query(value = "" +
            "SELECT topic_id, " +
            "SUM(correct_answers) AS correct_answers, " +
            "SUM(wrong_answers) AS wrong_answers, " +
            "SUM(correct_answers + wrong_answers) AS total " +
            "FROM tb_exercise " +
            "WHERE user_id = :userId " +
            "GROUP BY topic_id ", nativeQuery = true)
    List<TopicInfo> getTopicInfo(@Param("userId") int userId);
}
