package com.harbourspace.tracker.exercise.jdbc;

import com.harbourspace.tracker.activity.ActivityController;
import com.harbourspace.tracker.activity.jdbc.ActivityJdbcRepository;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseJdbcRepository {

    private final Logger logger = LoggerFactory.getLogger(ExerciseJdbcRepository.class);


    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Exercise> exerciseRowMapper = (rs, rowNum) -> new Exercise(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("activity_id"),
            rs.getTimestamp("start_time").toLocalDateTime(),
            rs.getInt("duration"),
            rs.getInt("kcal"));

    public ExerciseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Exercise> findAllByUserId(Long userId) {
        logger.debug("Selecting all exercises for user {}", userId);
        return jdbcTemplate.query(
                "SELECT * FROM exercises WHERE user_id = ?",
                exerciseRowMapper,
                userId);
    }

    public List<Exercise> findByCriteria(Long userId, LocalDate date, Integer minDuration, Integer maxDuration) {

        logger.debug("Selecting exercises for user {} with criteria", userId);
        return jdbcTemplate.query(
                "SELECT * FROM exercises WHERE user_id = ? AND DATE(start_time) = ? AND duration BETWEEN ? AND ?",
                exerciseRowMapper,
                userId, date, minDuration, maxDuration);
    }

    public Optional<Exercise> findByIdAndUserId(Long id, Long userId) {
        logger.debug("Selecting exercise {} for user {}", id, userId);
        List<Exercise> exercises = jdbcTemplate.query(
                "SELECT * FROM exercises WHERE id = ? AND user_id = ?",
                exerciseRowMapper,
                id, userId);
        return exercises.stream().findFirst();
    }

    public Exercise insert(NewExercise exercise, double kcal) {
        Exercise updatedExercises = jdbcTemplate.query(
                "SELECT * FROM FINAL TABLE (INSERT INTO exercises (user_id , activity_id , start_time , duration, kcal) VALUES (?, ?, ?, ?, ?))",
                this::rowMapper,
                exercise.userId(),
                exercise.activityId(),
                Timestamp.valueOf(exercise.startTime()),
                exercise.duration(),
                kcal
        ).get(0);

        return updatedExercises;
    }

    public Exercise update(Long id, Long userId, NewExercise updatedExercise, double kcal) {
        logger.debug("Updating exercise {} for user {}: {}", id, userId, updatedExercise);
        jdbcTemplate.update(
                "UPDATE exercises SET activity_id = ?, start_time = ?, duration = ?, kcal_burned = ? WHERE id = ? AND user_id = ?",
                updatedExercise.activityId(), updatedExercise.startTime(), updatedExercise.duration(), kcal, id, userId);
        return updatedExercise.toExercise(id);
    }

    public void deleteById(Long id) {
        logger.debug("Deleting exercise {}", id);
        jdbcTemplate.update("DELETE FROM exercises WHERE id = ?", id);
    }

    private Exercise rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Exercise(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("activity_id"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getInt("duration"),
                rs.getInt("kcal")
        );
    }
}
