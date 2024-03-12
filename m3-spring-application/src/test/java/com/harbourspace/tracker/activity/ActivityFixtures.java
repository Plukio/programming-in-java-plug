package com.harbourspace.tracker.activity;

import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;

import java.util.Arrays;
import java.util.List;

public class ActivityFixtures {
    // Predefined activities for testing
    public static final Activity running = new Activity(1L, 0L,  "Running", 10.0);
    public static final Activity jogging = new Activity(2L, 1L, "Jogging", 7.0);

    public static final Activity newUserActivity = new Activity(3L, 1L, "Swimming", 8.0);

    public static final NewActivity newActivity = new NewActivity(1L,  "Swimming", 10.0);

    public static final Activity updatedActivity = new Activity(3L, 1L,  "Swimming", 10.0);
    public static final List<Activity> systemActivities = List.of(running);
    public static final List<Activity> userActivities = List.of(jogging);
    public static final List<Activity> userDefinedActivities = List.of(jogging);

}
