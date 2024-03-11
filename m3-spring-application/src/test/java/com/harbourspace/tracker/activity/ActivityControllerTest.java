package com.harbourspace.tracker.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harbourspace.tracker.activity.model.Activity;
import com.harbourspace.tracker.activity.model.NewActivity;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.config.SecurityConfiguration;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

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


    @BeforeEach
    void setUp() {

        Mockito.when(activityService.getAllActivitiesForUser(1L))
                .thenReturn(Collections.singletonList(new Activity(1L, 1L, "USER", "Jogging", 7.0)));
        Mockito.when(activityService.getActivityById(1L, 1L))
                .thenReturn(Optional.of(new Activity(1L, 1L, "USER", "Jogging", 7.0)));

    }

    @Test
    @DisplayName("Get All Activities for Authorized User")
    void getAllActivitiesForAuthorizedUser() throws Exception {
        mockMvc.perform(get("/api/activities")
                        .param("userId", "1")
                        .header("Authorization", "Basic 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Jogging"));
    }




}
