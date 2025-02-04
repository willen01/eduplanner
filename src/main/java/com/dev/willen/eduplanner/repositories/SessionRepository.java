package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer sessionId, Integer userId);
}
