package com.dev.willen.eduplanner.dto;

import java.util.List;

public record RankingExerciseResponse(List<HightPerformanceDto> hightPerformance, List<LowPerformanceDto> lowPerfomance) {
}
