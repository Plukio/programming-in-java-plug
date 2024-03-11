package com.harbourspace.tracker.activity;

import com.harbourspace.tracker.activity.jdbc.ActivityJdbcRepository;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import com.harbourspace.tracker.exercise.ExerciseFixtures;
import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcRepository;
import com.harbourspace.tracker.exercise.model.Exercise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import com.harbourspace.tracker.activity.ActivityFixtures;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(ActivityJdbcRepository.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActivityJdbcRepositoryTest {

    @Autowired
    private ActivityJdbcRepository activityRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        // Setup test data
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS activities (" +
                "id SERIAL PRIMARY KEY, " +
                "user_id BIGINT NOT NULL, " +
                "type VARCHAR(255), " +
                "name VARCHAR(255), " +
                "kcal_per_minute DOUBLE PRECISION" +
                ");");

        // Insert some test activities
        jdbcTemplate.update("INSERT INTO activities (user_id, type, name, kcal_per_minute) VALUES (0, 'SYSTEM', 'Running', 10.0)");
        jdbcTemplate.update("INSERT INTO activities (user_id, type, name, kcal_per_minute) VALUES (1, 'USER', 'Jogging', 7.0)");
    }

    @Test
    @DisplayName("findAllByUserId should return both USER and SYSTEM activities for a given user")
    void findAllByUserId() {
        List<Activity> activities = activityRepository.findAllByUserId(1L);
        Assertions.assertFalse(activities.isEmpty());
        Assertions.assertEquals(2, activities.size());
    }


    @Test
    @DisplayName("findByIdAndUserId should return an activity by ID and user ID")
    void findByIdAndUserId() {
        Optional<Activity> activity = activityRepository.findByIdAndUserId(1L, 1L); // Assuming an ID of 1L exists
        Assertions.assertTrue(activity.isPresent());
    }

    @Test
    @DisplayName("insert should add a new USER activity type")
    void insert() {
        Activity newActivity = new Activity(null, 1L, "USER", "Cycling", 8.0);
        Activity insertedActivity = activityRepository.insert(1L, newActivity);
        Assertions.assertNotNull(insertedActivity);
        Assertions.assertEquals("Cycling", insertedActivity.name());
    }

    @Test
    @DisplayName("update should modify an existing USER activity type")
    void update() {

        NewActivity updatedActivity = new NewActivity(1L, "USER", "Updated Running", 11.0);
        Activity result = activityRepository.update(1L, 1L, updatedActivity);
        assertNotNull(result);
        Assertions.assertEquals("Updated Running", result.name());
    }

    @Test
    @DisplayName("deleteById should remove an activity")
    void deleteById() {
        Activity insertedActivity = activityRepository.insert(1L, ActivityFixtures.jogging);
        activityRepository.deleteById(1L, insertedActivity.id());
        Optional<Activity> foundExercise = activityRepository.findByIdAndUserId(1L, insertedActivity.id());
        Assertions.assertEquals(foundExercise, Optional.empty());
    }

}
