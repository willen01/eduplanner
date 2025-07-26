package com.dev.willen.eduplanner.dto;

import java.sql.Time;
import java.sql.Timestamp;

public record SessionInfo(int id, String subject_name, String topic_name, Time start_time, Time end_time,
                          Time duration, Timestamp createdAt
                          ) {
}
