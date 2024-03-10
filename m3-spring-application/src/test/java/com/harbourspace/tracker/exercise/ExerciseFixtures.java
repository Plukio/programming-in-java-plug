package com.harbourspace.tracker.exercise;

import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;

import java.time.LocalDateTime;
import java.util.List;

public class ExerciseFixtures {
    public static final Exercise exercise1 = new Exercise(1L, 1L, 1L, LocalDateTime.now().minusDays(1), 30);
    public static final Exercise exercise2 = new Exercise(2L, 1L, 2L, LocalDateTime.now(), 60);


    public static final NewExercise newExercise = new NewExercise(1L, 3L, LocalDateTime.now().plusDays(1), 45);


    public static final List<Exercise> exercises = List.of(exercise1, exercise2);
    public static final List<Exercise> exercises1 = List.of(exercise1);
}
