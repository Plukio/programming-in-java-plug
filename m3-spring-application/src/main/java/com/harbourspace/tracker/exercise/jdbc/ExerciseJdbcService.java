package com.harbourspace.tracker.exercise.jdbc;

import com.harbourspace.tracker.activity.jdbc.ActivityJdbcRepository;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.error.AuthorizationException;
import com.harbourspace.tracker.exercise.ExerciseService;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseJdbcService implements ExerciseService {

    private final Logger logger = LoggerFactory.getLogger(ExerciseJdbcService.class);
    private final ExerciseJdbcRepository exerciseRepository;
    private final AuthorizationService authorizationService;
    private final ActivityJdbcRepository activityJdbcRepository;

    public ExerciseJdbcService(ExerciseJdbcRepository exerciseRepository, AuthorizationService authorizationService, ActivityJdbcRepository activityJdbcRepository) {
        this.exerciseRepository = exerciseRepository;
        this.authorizationService = authorizationService;
        this.activityJdbcRepository = activityJdbcRepository;
    }

    @Override
    public List<Exercise> getAllExercisesForUser(Long userId) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        }
        else {
            logger.debug("Fetching all exercises for user {}", userId);
            return exerciseRepository.findAllByUserId(userId);
        }
    }

    @Override
    public List<Exercise> getExercisesByCriteria(Long userId, LocalDate date, Integer minDuration, Integer maxDuration) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        }
        else {
            logger.debug("Fetching exercises for user {} with criteria , Date: {}, Min Duration: {}, Max Duration: {}", userId, date, minDuration, maxDuration);
            return exerciseRepository.findByCriteria(userId, date, minDuration, maxDuration);
        }
    }

    @Override
    public Optional<Exercise> getExerciseById(Long userId, Long id) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        }
        else {
            logger.debug("Fetching exercise {} for user {}", id, userId);
            return exerciseRepository.findByIdAndUserId(id, userId);
        }
    }

    @Override
    public Exercise createExercise(Long userId, NewExercise exercise) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        } else {
            logger.debug("Adding new exercise for user {}: {}", userId, exercise);
            Optional<Activity> activity  = activityJdbcRepository.findByIdAndUserId(exercise.userId(), exercise.activityId());
            if (activity.isPresent()) {
                return exerciseRepository.insert(exercise, activity.get().kcalPerMinute() * exercise.duration());
            }
            return exerciseRepository.insert(exercise, 0.0);
        }
    }

    @Override
    public Exercise updateExercise(Long userId, Long id, NewExercise updatedExercise) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        }

        else {
            logger.debug("Updating exercise {} for user {}: {}", id, userId, updatedExercise);
            Optional<Activity> activity  = activityJdbcRepository.findByIdAndUserId(updatedExercise.userId(), updatedExercise.activityId());
            if (activity.isPresent()) {
                return exerciseRepository.update(id, userId, updatedExercise, activity.get().kcalPerMinute() * updatedExercise.duration());
            }
            return exerciseRepository.update(id, userId, updatedExercise, 0.0);
        }
    }

    @Override
    public void deleteExercise(Long userId, Long id) {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw unauthorized();
        }
        else {
            logger.debug("Deleting exercise {} for user {}", id, userId);
            exerciseRepository.deleteById(id);
        }
    }

    private AuthorizationException unauthorized() {
        return new AuthorizationException("User is not authorized for this operation.");
    }
}
