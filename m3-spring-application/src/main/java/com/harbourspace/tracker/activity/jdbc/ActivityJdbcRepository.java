package com.harbourspace.tracker.activity.jdbc;

import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActivityJdbcRepository {

    private static final Logger logger = LoggerFactory.getLogger(ActivityJdbcRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Activity> activityRowMapper = (rs, rowNum) -> new Activity(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getString("type"),
            rs.getString("name"),
            rs.getDouble("kcal_per_minute")
    );

    public List<Activity> findAllByUserId(Long userId) {
        logger.debug("Selecting all activity types for user {}", userId);
        return jdbcTemplate.query(
                "SELECT * FROM activities WHERE user_id = ? OR user_id = 0",
                activityRowMapper,
                userId);
    }


    public Optional<Activity> findByIdAndUserId(Long userId, Long id) {
        logger.debug("Selecting activity type by id {} for user {}", id, userId);
        return jdbcTemplate.query(
                "SELECT * FROM activities WHERE id = ? AND (user_id = ? OR user_id = 0)",
                new Object[]{id, userId},
                rs -> rs.next() ? Optional.of(activityRowMapper.mapRow(rs, 1)) : Optional.empty()
        );
    }

    public Activity insert(Long userId, Activity activity) {
        logger.debug("Inserting new USER activity type for user {}", userId);
        jdbcTemplate.update(
                "INSERT INTO activities (user_id, type, name, kcal_per_minute) VALUES (?, ?, ?, ?)",
                userId, "USER", activity.name(), activity.kcalPerMinute()
        );

        return new Activity(null, userId, "USER", activity.name(), activity.kcalPerMinute());
    }

    public Activity update(Long userId, Long id, NewActivity updatedActivity) {
        logger.debug("Updating USER activity type {} for user {}", id, userId);
        jdbcTemplate.update(
                "UPDATE activities SET name = ?, kcal_per_minute = ? WHERE id = ? AND user_id = ?",
                updatedActivity.name(), updatedActivity.kcalPerMinute(), id, userId
        );

        return new Activity(id, userId, "USER", updatedActivity.name(), updatedActivity.kcalPerMinute());
    }

    public void deleteById(Long userId, Long id) {
        logger.debug("Deleting USER activity type {} for user {}", id, userId);
        jdbcTemplate.update("DELETE FROM activities WHERE id = ? AND user_id = ?", id, userId);

    }

    public boolean isActivityTypeUsedInUserActivities(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_activities WHERE activity_id = ?",
                new Object[]{id},
                Integer.class
        );
        return count != null && count > 0;
    }

    public boolean isActivityNameUsedExcludingId(Long userId, String name, Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM activities WHERE user_id = ? AND name = ? AND id <> ?",
                new Object[]{userId, name, id},
                Integer.class
        );
        return count != null && count > 0;
    }

    public boolean isActivityNameUsed(Long userId, String name) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM activities WHERE user_id = ? AND name = ?",
                new Object[]{userId, name},
                Integer.class
        );
        return count != null && count > 0;
    }
}
