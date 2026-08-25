package com.sportsplatform.athlete.infrastructure.adapter.in.rest;

import com.sportsplatform.athlete.application.port.in.CreateAthleteUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthleteByIdUseCase;
import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper.AthleteRestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AthleteController.class)
class AthleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateAthleteUseCase createAthleteUseCase;

    @MockitoBean
    private GetAthleteByIdUseCase getAthleteByIdUseCase;

    @MockitoBean
    private AthleteRestMapper mapper;

    @Test
    void shouldGetAthleteById() throws Exception {

        UUID athleteId = UUID.randomUUID();

        Athlete athlete = Athlete.restore(
                athleteId,
                "Andres",
                "Alfaro",
                "andres@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE,
                true
        );

        var response =
                new com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse(
                        athleteId,
                        "Andres",
                        "Alfaro",
                        "andres@example.com",
                        LocalDate.of(1985, 5, 20),
                        Gender.MALE,
                        true
                );

        when(getAthleteByIdUseCase.getById(athleteId))
                .thenReturn(athlete);

        when(mapper.toResponse(athlete))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/athletes/{athleteId}", athleteId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(athleteId.toString()))
                .andExpect(jsonPath("$.firstName").value("Andres"))
                .andExpect(jsonPath("$.lastName").value("Alfaro"))
                .andExpect(jsonPath("$.email").value("andres@example.com"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldReturnNotFoundWhenAthleteDoesNotExist() throws Exception {

        UUID athleteId = UUID.randomUUID();

        when(getAthleteByIdUseCase.getById(athleteId))
                .thenThrow(new AthleteNotFoundException(athleteId));

        mockMvc.perform(
                        get("/api/v1/athletes/{athleteId}", athleteId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("ATHLETE_NOT_FOUND"))
                .andExpect(jsonPath("$.message")
                        .value("Athlete not found: " + athleteId));
    }
}