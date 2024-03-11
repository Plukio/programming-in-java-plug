package com.harbourspace.tracker.activity.jdbc;

import com.harbourspace.tracker.activity.ActivityService;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.config.ApplicationConfiguration;
import com.harbourspace.tracker.error.AuthorizationException;
import com.harbourspace.tracker.error.ConflictException;
import com.harbourspace.tracker.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityJdbcService implements ActivityService {

    private final ActivityJdbcRepository activityRepository;
    private final AuthorizationService authorizationService;

    @Autowired
    public ActivityJdbcService(ActivityJdbcRepository activityRepository, AuthorizationService authorizationService) {
        this.activityRepository = activityRepository;
        this.authorizationService = authorizationService;
    }

    public List<Activity> getAllActivitiesForUser(Long userId) throws AuthorizationException {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw new AuthorizationException("User is not authorized for this operation.");
        }
        return activityRepository.findAllByUserId(userId);
    }


    public Optional<Activity> getActivityById(Long userId, Long id) throws AuthorizationException {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw new AuthorizationException("User is not authorized for this operation.");
        }
        Optional<Activity> activity = activityRepository.findByIdAndUserId(userId, id);
        if (activity.isPresent() && activity.get().type().equals("SYSTEM") && !userId.equals(ApplicationConfiguration.SYSTEM_USER_ID)) {
            throw new AuthorizationException("Access to SYSTEM activity type is not allowed.");
        }
        else {
            return activity;
        }
    }

    public Activity addActivity(Long userId, Activity activity) throws ConflictException, AuthorizationException {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw new AuthorizationException("User is not authorized for this operation.");
        }
        if (activityRepository.isActivityNameUsed(userId, activity.type())) {
            throw new ConflictException("Activity name must be unique within the user profile.");
        }
        else {
            return activityRepository.insert(userId, activity);
        }
    }

    public Activity updateActivity(Long userId, Long id, NewActivity updatedActivity) throws NotFoundException, ConflictException, AuthorizationException {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw new AuthorizationException("User is not authorized for this operation.");
        }
        Optional<Activity> existingActivity = activityRepository.findByIdAndUserId(userId, id);
        if (existingActivity.isPresent() && existingActivity.get().type().equals("SYSTEM")) {
            throw new AuthorizationException("Modifying SYSTEM activity type is not allowed.");
        }
        if (activityRepository.isActivityNameUsedExcludingId(userId, updatedActivity.name(), id)) {
            throw new ConflictException("Activity name must be unique within the user profile.");
        }
        else {
            return activityRepository.update(userId, id, updatedActivity);
        }
    }

    public void deleteActivity(Long userId, Long id) throws NotFoundException, AuthorizationException {
        if (!userId.equals(authorizationService.getCurrentUserId())) {
            throw new AuthorizationException("User is not authorized for this operation.");
        }
        Optional<Activity> activity = activityRepository.findByIdAndUserId(userId, id);
        if (activity.isPresent() && activity.get().type().equals("SYSTEM")) {
            throw new AuthorizationException("Deleting SYSTEM activity type is not allowed.");
        }
        if (activityRepository.isActivityTypeUsedInUserActivities(id)) {
            throw new ConflictException("Cannot delete activity type as it is used in user activities.");
        }
        else {
            activityRepository.deleteById(userId, id);
        }
    }

}
