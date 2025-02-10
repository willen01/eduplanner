package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Integer> {
    public List<Timer> findAllByUserId(String userId);
}
