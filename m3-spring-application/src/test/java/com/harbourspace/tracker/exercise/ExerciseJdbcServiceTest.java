package com.harbourspace.tracker.exercise;


import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.error.AuthorizationException;
import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcRepository;
import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcService;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ExerciseJdbcServiceTest {

    private final ExerciseJdbcRepository exerciseRepository = Mockito.mock(ExerciseJdbcRepository.class);
    private final AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);

    private final ExerciseService exerciseService = new ExerciseJdbcService(exerciseRepository, authorizationService);

    @BeforeEach
    public void setup() {
        when(authorizationService.isSystem()).thenReturn(true);
        when(authorizationService.getCurrentUserId()).thenReturn(1L); // Assuming getCurrentUserId method
        when(exerciseRepository.findAllByUserId(1L)).thenReturn(ExerciseFixtures.exercises1);
        when(exerciseRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(ExerciseFixtures.exercise1));
    }

    @Test
    @DisplayName("should return all exercises for a user")
    public void testGetAllExercisesForUser() {
        Assertions.assertEquals(ExerciseFixtures.exercises1, exerciseService.getAllExercisesForUser(1L));
        Mockito.verify(exerciseRepository).findAllByUserId(1L);
    }

    @Test
    @DisplayName("should get an exercise by id for a user")
    public void testGetExerciseById() {
        Assertions.assertEquals(Optional.of(ExerciseFixtures.exercise1), exerciseService.getExerciseById(1L, 1L));
        Mockito.verify(exerciseRepository).findByIdAndUserId(1L, 1L);
    }

    @Test
    @DisplayName("should create an exercise for a user")
    public void testCreateExercise() {
        NewExercise newExercise = new NewExercise(1L, 2L, LocalDateTime.of(2021, 1, 1, 10, 0), 60); // Simplified constructor for example
        when(exerciseRepository.insert(newExercise)).thenReturn(ExerciseFixtures.exercise1);

        Assertions.assertEquals(ExerciseFixtures.exercise1, exerciseService.createExercise(1L, newExercise));
        Mockito.verify(exerciseRepository).insert(newExercise);
    }

    @Test
    @DisplayName("getExercisesByCriteria returns exercises based on criteria")
    void getExercisesByCriteriaReturnsExercises() {
        Long userId = 1L; // Example user ID
        Long activityId = 2L;
        LocalDate date = LocalDate.of(2021, 1, 1);
        Integer minDuration = 30;
        Integer maxDuration = 60;

        Exercise exampleExercise = new Exercise(1L, userId, activityId, date.atStartOfDay(), 45);
        List<Exercise> expectedExercises = Collections.singletonList(exampleExercise);

        when(exerciseRepository.findByCriteria(userId, date, minDuration, maxDuration)).thenReturn(expectedExercises);

        List<Exercise> result = exerciseService.getExercisesByCriteria(userId, date, minDuration, maxDuration);

        Assertions.assertEquals(result, expectedExercises);
    }

    @Test
    @DisplayName("updateExercise should update exercise when authorized")
    public void testUpdateExerciseAuthorized() {

        NewExercise newExercise = ExerciseFixtures.newExercise;
        Exercise expectedUpdatedExercise = newExercise.toExercise(1L);

        // Mock behavior
        when(authorizationService.getCurrentUserId()).thenReturn(1L); // Authorized user
        when(exerciseRepository.update(1L, 1L, newExercise)).thenReturn(expectedUpdatedExercise);

        Exercise actualUpdatedExercise = exerciseService.updateExercise(1L, 1L, newExercise);
        Assertions.assertEquals(expectedUpdatedExercise, actualUpdatedExercise);

        Mockito.verify(exerciseRepository).update(1L, 1L, newExercise);
    }

    @Test
    @DisplayName("updateExercise should throw exception when not authorized")
    public void testUpdateExerciseNotAuthorized() {
        NewExercise newExercise = ExerciseFixtures.newExercise;

        when(authorizationService.getCurrentUserId()).thenReturn(2L); // Different user, not authorized

        Assertions.assertThrows(AuthorizationException.class, () -> {
            exerciseService.updateExercise(1L, 1L, newExercise);
        });

        Mockito.verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    @DisplayName("deleteExercise should delete exercise when authorized")
    public void testDeleteExerciseAuthorized() {
        when(authorizationService.getCurrentUserId()).thenReturn(1L); // Authorized user

        exerciseService.deleteExercise(1L, 1L); // Assuming the first argument is userId and the second is exerciseId

        Mockito.verify(exerciseRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteExercise should throw exception when not authorized")
    public void testDeleteExerciseNotAuthorized() {
        when(authorizationService.getCurrentUserId()).thenReturn(2L); // Different user, not authorized

        Assertions.assertThrows(AuthorizationException.class, () -> {
            exerciseService.deleteExercise(1L, 1L);
        });

        Mockito.verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    @DisplayName("getAllExercisesForUser should throw exception when not authorized")
    public void testGetAllExercisesForUserNotAuthorized() {
        when(authorizationService.getCurrentUserId()).thenReturn(2L); // Different user
        Assertions.assertThrows(AuthorizationException.class, () -> exerciseService.getAllExercisesForUser(1L));
        Mockito.verifyNoMoreInteractions(exerciseRepository);
    }

}
