package com.harbourspace.tracker.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harbourspace.tracker.authorization.AuthorizationService;
import com.harbourspace.tracker.config.SecurityConfiguration;
import com.harbourspace.tracker.exercise.jdbc.ExerciseJdbcService;
import com.harbourspace.tracker.exercise.model.Exercise;
import com.harbourspace.tracker.exercise.model.NewExercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciseController.class)
@Import(SecurityConfiguration.class)
public class ExerciseControllerTest {

    @MockBean
    private ExerciseJdbcService exerciseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private AuthorizationService authorizationService;

    private Exercise exercise;
    private NewExercise newExercise;

    @BeforeEach
    void setUp() {
        newExercise = new NewExercise(1L, 1L, LocalDateTime.now(), 30);
        exercise = new Exercise(1L, 1L, 1L, LocalDateTime.now(), 30);

        Mockito.when(exerciseService.getAllExercisesForUser(Mockito.anyLong())).thenReturn(Arrays.asList(exercise));
        Mockito.when(exerciseService.getExercisesByCriteria(Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(exercise));
        Mockito.when(exerciseService.getExerciseById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(exercise));
        Mockito.when(exerciseService.createExercise(Mockito.anyLong(), Mockito.any(NewExercise.class))).thenReturn(exercise);
    }

    @Test
    @DisplayName("GET /api/exercises - Success")
    @WithMockUser
    void testGetAllExercisesForUser() throws Exception {
        Mockito.when(authorizationService.getCurrentUserId()).thenReturn(1L);
        mockMvc.perform(get("/api/exercises")
                        .param("userId", "1").header("Authorization", "Basic 0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(exercise.id()));
    }

    @Test
    @DisplayName("GET /api/exercises/filter - Success")
    @WithMockUser
    void testGetExercisesByCriteria() throws Exception {
        LocalDate date = LocalDate.of(2022, 1, 1);
        List<Exercise> exercises = List.of(
                new Exercise(1L, 1L, 1L, LocalDateTime.now(), 30),
                new Exercise(2L, 1L, 2L, LocalDateTime.now().minusDays(1), 45)
        );

        Mockito.when(exerciseService.getExercisesByCriteria(1L, date, 20, 60)).thenReturn(exercises);

        mockMvc.perform(get("/api/exercises/filter")
                        .param("userId", "1")
                        .param("date", date.toString())
                        .param("minDuration", "20")
                        .param("maxDuration", "60").header("Authorization", "Basic 0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(exercises.size()));
    }

    @Test
    @DisplayName("GET /api/exercises/{id} - Success")
    @WithMockUser
    void testGetExerciseById() throws Exception {
        Exercise exercise = new Exercise(1L, 1L, 1L, LocalDateTime.now(), 30);

        Mockito.when(exerciseService.getExerciseById(1L, 1L)).thenReturn(Optional.of(exercise));

        mockMvc.perform(get("/api/exercises/{id}", 1)
                        .param("userId", "1")
                        .header("Authorization", "Basic 0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(exercise.id()));
    }


    @Test
    @DisplayName("POST /api/exercises - Success")
    @WithMockUser
    void testCreateExercise() throws Exception {
        NewExercise newExercise = new NewExercise(1L, 1L, LocalDateTime.now(), 30);
        Exercise createdExercise = new Exercise(1L, 1L, 1L, newExercise.startTime(), newExercise.duration());

        Mockito.when(exerciseService.createExercise(Mockito.anyLong(), Mockito.any(NewExercise.class))).thenReturn(createdExercise);

        String newExerciseJson = objectMapper.writeValueAsString(newExercise);

        mockMvc.perform(post("/api/exercises")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newExerciseJson)
                        .header("Authorization", "Basic 0"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdExercise.id()));
    }


    @Test
    @DisplayName("DELETE /api/exercises/{id} - Success")
    @WithMockUser
    void testDeleteExercise() throws Exception {
        Mockito.doNothing().when(exerciseService).deleteExercise(1L, 1L);

        mockMvc.perform(delete("/api/exercises/{id}", 1)
                        .param("userId", "1").header("Authorization", "Basic 0"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /api/exercises/{id} - Success")
    @WithMockUser
    void testUpdateExercise() throws Exception {
        NewExercise updatedExercise = new NewExercise(1L, 1L, LocalDateTime.now(), 45);
        Exercise updated = new Exercise(1L, 1L, 1L, updatedExercise.startTime(), updatedExercise.duration());

        Mockito.when(exerciseService.updateExercise(1L, 1L, updatedExercise)).thenReturn(updated);


        String updatedExerciseJson = objectMapper.writeValueAsString(updatedExercise);

        mockMvc.perform(put("/api/exercises/{id}", 1)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedExerciseJson).header("Authorization", "Basic 0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.duration").value(updated.duration()));
    }





}
