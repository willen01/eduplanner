package com.dev.willen.eduplanner.dto;

import com.dev.willen.eduplanner.entities.Topic;

import java.util.Date;
import java.util.Set;

public record SubjectResponse(int id, String name, Date createdAt, Set<Topic> topics) {
}
