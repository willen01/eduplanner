package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    List<Exercise> findAllByUserId(int userId);
}
