package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findById(int subjectId);
}
