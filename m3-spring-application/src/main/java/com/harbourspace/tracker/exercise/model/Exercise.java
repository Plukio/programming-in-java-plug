package com.harbourspace.tracker.exercise.model;

import java.time.LocalDateTime;

public record Exercise(
    Long id,
    Long userId,
    Long activityId,
    LocalDateTime startTime,
    Integer duration
) {

}
