package com.harbourspace.tracker.activity;

import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> getAllActivitiesForUser(Long userId);

    Optional<Activity> getActivityById(Long userId, Long id);

    Activity addActivity(Long userId, Activity activity);

    Activity updateActivity(Long userId, Long id, NewActivity updatedActivity);

    void deleteActivity(Long userId, Long id);
}
