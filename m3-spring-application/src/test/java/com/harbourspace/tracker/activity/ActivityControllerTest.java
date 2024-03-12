package com.harbourspace.tracker.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harbourspace.tracker.activity.jdbc.ActivityJdbcRepository;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.config.SecurityConfiguration;
import com.harbourspace.tracker.error.ConflictException;
import com.harbourspace.tracker.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityController.class)
@Import(SecurityConfiguration.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private ActivityJdbcRepository activityRepository;


    @BeforeEach
    void setUp() {

        Mockito.when(activityService.getAllActivitiesForUser(1L))
                .thenReturn(Collections.singletonList(new Activity(1L, 1L,  "Jogging", 7.0)));
        Mockito.when(activityService.getActivityById(1L, 1L))
                .thenReturn(Optional.of(new Activity(1L, 1L,  "Jogging", 7.0)));

    }

    @Test
    @DisplayName("Get All Activities /api/activities")
    void getAllActivitiesForAuthorizedUser() throws Exception {
        mockMvc.perform(get("/api/activities")
                        .param("userId", "1")
                        .header("Authorization", "Basic 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Jogging"));
    }


    @Test
    @DisplayName("Create Activity /api/activities")
    void createActivityForAuthorizedUser() throws Exception {
        NewActivity newActivity = new NewActivity(1L, "Cycling", 8.5);
        Activity createdActivity = new Activity(2L, 1L,  "Cycling", 8.5);

        Mockito.when(activityService.addActivity(Mockito.eq(1L), Mockito.any(NewActivity.class)))
                .thenReturn(createdActivity);

        mockMvc.perform(post("/api/activities")
                        .param("userId", "1")
                        .header("Authorization", "Basic 1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newActivity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Cycling"))
                .andExpect(jsonPath("$.kcalPerMinute").value(8.5));
    }

    @Test
    @DisplayName("Update Activity /api/activities/{id}")
    void updateActivityForAuthorizedUser() throws Exception {
        NewActivity updatedActivity = new NewActivity(1L ,"Updated Cycling", 9.0);
        Activity updated = new Activity(1L, 1L, "Updated Cycling", 9.0);

        Mockito.when(activityService.updateActivity(Mockito.eq(1L), Mockito.eq(1L), Mockito.any(NewActivity.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/activities/{id}", 1)
                        .param("userId", "1")
                        .header("Authorization", "Basic 1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedActivity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Cycling"))
                .andExpect(jsonPath("$.kcalPerMinute").value(9.0));
    }

    @Test
    @DisplayName("Delete Activity /api/activities/{id}")
    void deleteActivityForAuthorizedUser() throws Exception {
        Mockito.doNothing().when(activityService).deleteActivity(1L, 1L);

        mockMvc.perform(delete("/api/activities/{id}", 1)
                        .param("userId", "1")
                        .header("Authorization", "Basic 1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Attempt to Delete Activity That Is In Use")
    void attemptToDeleteActivityInUse() throws Exception {
        long userId = 1L;
        long activityId = 1L;

        doThrow(new ConflictException("Cannot delete activity type as it is used in user activities."))
                .when(activityService).deleteActivity(userId, activityId);

        mockMvc.perform(delete("/api/activities/{id}", activityId)
                        .param("userId", String.valueOf(userId))
                        .header("Authorization", "Basic 1"))
                .andExpect(status().isConflict());
    }





}
