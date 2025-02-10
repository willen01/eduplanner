package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Integer> {
}
