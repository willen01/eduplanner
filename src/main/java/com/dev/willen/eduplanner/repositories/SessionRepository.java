package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.dto.SessionInfo;
import com.dev.willen.eduplanner.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query(value = "SELECT " +
            "se.id, " +
            "su.name AS subject_name, " +
            "t.name AS topic_name, " +
            "se.start_time, " +
            "se.end_time, " +
            "se.total, " +
            "se.created_at " +
            "FROM tb_session se " +
            "JOIN tb_user usr ON se.user_id = usr.id " +
            "JOIN tb_subject su ON se.subject_id = su.id " +
            "JOIN tb_topic t ON se.topic_id = t.id " +
            "WHERE usr.email = :userEmail", nativeQuery = true)
    List<SessionInfo> findSessionInfoByUserEmail(String userEmail);
}
