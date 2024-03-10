package com.harbourspace.tracker.exercise.model;




import java.time.LocalDateTime;

public record NewExercise(
        Long userId,
        Long activityId,
        LocalDateTime startTime,
        Integer duration
) {
    public Exercise toExercise(Long Exid) {

        return new Exercise(Exid, this.userId, this.activityId, this.startTime, this.duration);
    }

}