package com.dev.willen.eduplanner.dto;

import java.time.Instant;

public record ExerciseResponse(
        int id,
        String Subject,
        String topic,
        int correctAnswers,
        int wrongAnswers,
        double performance,
        Instant createdAt) {
}
