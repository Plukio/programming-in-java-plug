package com.harbourspace.tracker.exercise;

import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcRepository;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(ExerciseJdbcRepository.class)
public class ExerciseJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExerciseJdbcRepository exerciseRepository;

    @BeforeEach
    public void setup() {

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS exercises (" +
                        "id SERIAL PRIMARY KEY, " +
                        "user_id BIGINT NOT NULL, " +
                        "activity_id BIGINT NOT NULL, " +
                        "start_time TIMESTAMP NOT NULL, " +
                        "duration INTEGER NOT NULL, " +
                        "kcal_burned DOUBLE" +
                        ");"
        );

        // Clear the table before each test to ensure a clean state
        jdbcTemplate.update("DELETE FROM exercises");

        // Insert fixture data into the exercises table
        ExerciseFixtures.exercises.forEach(exercise ->
                jdbcTemplate.update(
                        "INSERT INTO exercises (user_id, activity_id, start_time, duration) VALUES (?, ?, ?, ?)",
                        exercise.userId(), exercise.activityId(), exercise.startTime(), exercise.duration()
                )
        );
    }

    @Test
    @DisplayName("should select all exercises for a user")
    public void testFindAllByUserId() {

        List<Exercise> exercises = exerciseRepository.findAllByUserId(1L);
        Assertions.assertEquals(2, exercises.size());
    }

    @Test
    @DisplayName("should select exercise by ID and user ID")
    public void testFindByIdAndUserId() {
        Optional<Exercise> foundExercise = exerciseRepository.findByIdAndUserId(ExerciseFixtures.exercise1.id(), ExerciseFixtures.exercise1.userId());
        Assertions.assertNotNull(foundExercise);

    }

    @Test
    @DisplayName("should insert a new exercise")
    public void testInsert() {
        Exercise insertedExercise = exerciseRepository.insert( ExerciseFixtures.newExercise); // Assuming insert returns Exercise
        Assertions.assertNotNull(insertedExercise);

    }

    @Test
    @DisplayName("should update an existing exercise")
    public void testUpdate() {
        Exercise insertedExercise = exerciseRepository.insert(ExerciseFixtures.newExercise);
        NewExercise updatedExercise = new NewExercise(insertedExercise.userId(), insertedExercise.activityId(), insertedExercise.startTime(), 60);
        Exercise result = exerciseRepository.update(insertedExercise.id(), insertedExercise.userId(), updatedExercise);
        Assertions.assertEquals(60, result.duration());

    }

    @Test
    @DisplayName("should delete an exercise by ID")
    public void testDeleteById() {
        Exercise insertedExercise = exerciseRepository.insert(ExerciseFixtures.newExercise);
        exerciseRepository.deleteById(insertedExercise.id());
        Optional<Exercise> foundExercise = exerciseRepository.findByIdAndUserId(insertedExercise.id(), 1L);
        Assertions.assertEquals(foundExercise, Optional.empty());
    }


}
