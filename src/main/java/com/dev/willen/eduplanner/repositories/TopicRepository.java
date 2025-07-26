package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findById(Integer integer);

    List<Topic> findAllByUserId(Integer userId);
}
