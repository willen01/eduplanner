package com.dev.willen.eduplanner.dto;

import java.time.LocalTime;

public record SaveTimerDto(LocalTime start, LocalTime end, int subjectId, int topicId) {
}
