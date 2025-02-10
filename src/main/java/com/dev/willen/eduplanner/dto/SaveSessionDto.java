package com.dev.willen.eduplanner.dto;

import java.time.LocalTime;

public record SaveSessionDto(LocalTime start, LocalTime end, int subjectId, int topicId) {
}
