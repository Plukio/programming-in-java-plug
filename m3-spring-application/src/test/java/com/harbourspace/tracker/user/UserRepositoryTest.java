package com.harbourspace.tracker.user;

import com.harbourspace.tracker.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(UserRepository.class)
public class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should select all users")
    public void testSelectAll() {
        Assertions.assertEquals(UserFixtures.users, userRepository.selectAll());
    }

    @Test
    @DisplayName("should select user by id")
    public void testSelectUserById() {
        Assertions.assertEquals(UserFixtures.user1, userRepository.selectById(1L));
    }

    @Test
    @DisplayName("should insert user")
    public void testInsertUser() {
        Assertions.assertInstanceOf(User.class, userRepository.insert(UserFixtures.newUser));
    }

    @Test
    @DisplayName("should update user")
    public void testUpdateUser() {
        userRepository.insert(UserFixtures.newUser);
        userRepository.update(3L, UserFixtures.user3Updated);
        Assertions.assertEquals(UserFixtures.user3Updated, userRepository.selectById(UserFixtures.user3Updated.id()));
    }

    @Test
    @DisplayName("should delete user")
    public void testDeleteUser() {
        userRepository.insert(UserFixtures.newUser);
        userRepository.delete(3L);
        Assertions.assertNull(userRepository.selectById(3L));

    }
}
