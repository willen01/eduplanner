package com.dev.willen.eduplanner.dto;

public record InfoUserResponse(
        int totalAnswers,
        int totalCorrectAnswers,
        int totalWrongAnswers,
        RateResponse performance
    ) {
}
