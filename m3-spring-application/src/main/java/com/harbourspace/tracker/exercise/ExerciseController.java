package com.harbourspace.tracker.exercise;

import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcService;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseJdbcService exerciseService;

    public ExerciseController(ExerciseJdbcService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping()
    public ResponseEntity<List<Exercise>> getAllExercisesForUser(@RequestParam Long userId) {
        return ResponseEntity.ok(exerciseService.getAllExercisesForUser(userId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Exercise>> getExercisesByCriteria(
            @RequestParam Long userId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration) {
        return ResponseEntity.ok(exerciseService.getExercisesByCriteria(userId, date, minDuration, maxDuration));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Exercise>> getExerciseById(@RequestParam Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(userId, id));
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestParam Long userId, @RequestBody NewExercise newExercise) {
        Exercise exercise = exerciseService.createExercise(userId, newExercise);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(
            @RequestParam Long userId,
            @PathVariable Long id,
            @RequestBody NewExercise updatedExercise) {
        Exercise exercise = exerciseService.updateExercise(userId, id, updatedExercise);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@RequestParam Long userId, @PathVariable Long id) {
        exerciseService.deleteExercise(userId, id);
        return ResponseEntity.noContent().build();
    }
}
