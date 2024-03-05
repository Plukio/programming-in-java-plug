package com.harbourspace.tracker.user;

import com.harbourspace.tracker.error.AuthorizationException;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.user.model.NewUser;
import com.harbourspace.tracker.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public UserService(UserRepository userRepository, AuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    List<User> getUsers() {
        if (authorizationService.isSystem()) {
            logger.debug("Getting all users");
            return userRepository.selectAll();
        } else throw unauthorized();
    }

    User getUserById(long id) {
        if (authorizationService.isSystem()) {
            logger.debug("Getting user " + id);
            return userRepository.selectById(id);
        } else throw unauthorized();
    }

    User createUser(NewUser user) {
        if (authorizationService.isSystem()) {
            logger.debug("Creating new user: " + user);
            return userRepository.insert(user);
        } else throw unauthorized();
    }

    User updateUser(long id, User user) {
        if (authorizationService.isSystem()) {
            logger.debug("Updating user " + id + ": "+ user);
            return userRepository.update(id, user);
        } else throw unauthorized();
    }

    void deleteUser(long id) {
        if (authorizationService.isSystem()) {
            logger.debug("Deleting user " + id);
            userRepository.delete(id);
        } else throw unauthorized();
    }

    private AuthorizationException unauthorized() {
        var authorizationException = new AuthorizationException("User is not authorized for this operation.");
        logger.error(authorizationException.getMessage());
        return authorizationException;
    }
}
