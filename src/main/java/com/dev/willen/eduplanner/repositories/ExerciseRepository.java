package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    List<Exercise> findAllByUserId(int userId);

    @Query(value = "select * from tb_exercise where user_id = :userId " +
            "order by tb_exercise.correct_answers desc", nativeQuery = true)
    List<Exercise> findHightPerformance(@Param("userId") int userId);

    @Query(value = "select * from tb_exercise where user_id = :userId " +
            "order by tb_exercise.correct_answers asc", nativeQuery = true)
    List<Exercise> findLowPerformance(@Param("userId") int userId);

    @Query(value = "select * from tb_exercise where user_id = :userId " +
            "order by tb_exercise.correct_answers desc limit :limit", nativeQuery = true)
    List<Exercise> findHightPerformance(@Param("userId") int userId, int limit);

    @Query(value = "select * from tb_exercise where user_id = :userId " +
            "order by tb_exercise.correct_answers asc limit :limit", nativeQuery = true)
    List<Exercise> findLowPerformance(@Param("userId") int userId, int limit);
}
