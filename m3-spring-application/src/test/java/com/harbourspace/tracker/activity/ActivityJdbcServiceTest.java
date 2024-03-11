package com.harbourspace.tracker.activity;

import com.harbourspace.tracker.activity.jdbc.ActivityJdbcRepository;
import com.harbourspace.tracker.activity.jdbc.ActivityJdbcService;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.error.AuthorizationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ActivityJdbcServiceTest {

    private final ActivityJdbcRepository activityRepository = Mockito.mock(ActivityJdbcRepository.class);
    private final AuthorizationService authorizationService = Mockito.mock(AuthorizationService.class);
    private final ActivityService activityService = new ActivityJdbcService(activityRepository, authorizationService);

    @BeforeEach
    public void setup() {
        when(authorizationService.getCurrentUserId()).thenReturn(1L); // Assuming a currentUserId method
        when(activityRepository.findAllByUserId(1L)).thenReturn(ActivityFixtures.userActivities);
        when(activityRepository.findByIdAndUserId(0L, 1L)).thenReturn(Optional.of(ActivityFixtures.running));
        when(activityRepository.findByIdAndUserId(1L, 2L)).thenReturn(Optional.of(ActivityFixtures.jogging));
    }

    @Test
    @DisplayName("should return all activities for a user")
    public void testGetAllActivitiesForUser() {
        List<Activity> activities = activityService.getAllActivitiesForUser(1L);
        Assertions.assertFalse(activities.isEmpty(), "Should return activities for the user");
        verify(activityRepository).findAllByUserId(1L);
    }

    @Test
    @DisplayName("should get an activity by id for a user")
    public void testGetActivityById() {
        Optional<Activity> activity = activityService.getActivityById(0L, 1L);
        Assertions.assertTrue(activity.isPresent(), "Should return the specified activity for the user");
        verify(activityRepository).findByIdAndUserId(0L, 1L);
    }

    // Example test case for testing activity creation
    @Test
    @DisplayName("should create an activity for a user")
    public void testCreateActivity() {
        Activity newActivity = new Activity(null, 1L, "USER", "New Activity", 5.0);
        when(activityRepository.insert(1L, newActivity)).thenReturn(ActivityFixtures.newUserActivity);

        Activity createdActivity = activityService.addActivity(1L, newActivity);
        assertNotNull(createdActivity, "Should create a new activity for the user");
        verify(activityRepository).insert(1L, newActivity);
    }

    @Test
    @DisplayName("Unauthorized user cannot update activity")
    void unauthorizedUserCannotUpdateActivity() {
        when(authorizationService.getCurrentUserId()).thenReturn(99L);

        Assertions.assertThrows(AuthorizationException.class, () ->
                activityService.updateActivity(1L, 2L, ActivityFixtures.newActivity));

        verify(activityRepository, never()).update(1L, 2L, ActivityFixtures.newActivity);
    }

    @Test
    @DisplayName("Delete existing user activity")
    void deleteExistingUserActivity() {

        Mockito.doNothing().when(activityRepository).deleteById(1L, 2L);

        activityService.deleteActivity(1L, 2L);
        verify(activityRepository).deleteById(1L, 2L);
    }

    @Test
    @DisplayName("Unauthorized user cannot delete activity")
    void unauthorizedUserCannotDeleteActivity() {

        when(authorizationService.getCurrentUserId()).thenReturn(99L);
        Assertions.assertThrows(AuthorizationException.class, () ->
                activityService.deleteActivity(1L, 2L));

        verify(activityRepository, never()).deleteById(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Update existing user activity with authorization")
    void updateExistingUserActivityWithAuthorization() {

        when(authorizationService.getCurrentUserId()).thenReturn(1L);
        when(activityRepository.update(1L, 2L, ActivityFixtures.newActivity)).thenReturn(ActivityFixtures.updatedActivity);
        Activity updatedActivity = activityService.updateActivity(1L, 2L, ActivityFixtures.newActivity);

        Assertions.assertNotNull(updatedActivity);
        Assertions.assertEquals(ActivityFixtures.updatedActivity.name(), updatedActivity.name());
        Mockito.verify(activityRepository).update(1L, 2L, ActivityFixtures.newActivity);
    }
}
