package com.harbourspace.tracker.user;

import com.harbourspace.tracker.user.model.NewUser;
import com.harbourspace.tracker.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    protected final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<User> selectAll() {
        logger.debug("Selecting all users");
        return jdbcTemplate.query("SELECT * FROM users", this::rowMapper);
    }

    public User selectById(Long id) {
        logger.debug("Selecting user " + id);
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ? LIMIT 1", this::rowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    User insert(NewUser user) {
        logger.debug("Inserting new user: " + user);
        return jdbcTemplate.query(
                "SELECT * FROM FINAL TABLE (INSERT INTO users (name) VALUES (?))",
                this::rowMapper,
                user.name()
        ).stream().findFirst().orElse(null);
    }

    @Transactional
    User update(long id, User user) {
        logger.debug("Updating user " + id + ": " + user);
        return jdbcTemplate.query(
                "SELECT * FROM FINAL TABLE (UPDATE users SET name = ? WHERE id = ?)",
                this::rowMapper,
                user.name(),
                id
        ).stream().findFirst().orElse(null);
    }

    @Transactional
    void delete(Long id) {
        logger.debug("Deleting user " + id);
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    private User rowMapper(ResultSet rs, int i) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
