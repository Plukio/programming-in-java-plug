package com.harbourspace.tracker.exercise;

import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {


    List<Exercise> getAllExercisesForUser(Long userId);

    List<Exercise> getExercisesByCriteria(Long userId, LocalDate date, Integer minDuration, Integer maxDuration);

    Optional<Exercise> getExerciseById(Long userId, Long id);

    Exercise createExercise(Long userId, NewExercise exercise);

    Exercise updateExercise(Long userId, Long id, NewExercise updatedExercise);

    void deleteExercise(Long userId, Long id);
}