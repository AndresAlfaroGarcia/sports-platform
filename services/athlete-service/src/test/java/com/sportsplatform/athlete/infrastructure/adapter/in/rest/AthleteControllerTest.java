package com.sportsplatform.athlete.infrastructure.adapter.in.rest;

import com.sportsplatform.athlete.application.command.UpdateAthleteCommand;
import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.application.port.in.CreateAthleteUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthleteByIdUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthletesUseCase;
import com.sportsplatform.athlete.application.port.in.UpdateAthleteUseCase;
import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.PagedAthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.UpdateAthleteRequest;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper.AthleteRestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockitoBean
    private GetAthletesUseCase getAthletesUseCase;

    @MockitoBean
    private UpdateAthleteUseCase updateAthleteUseCase;

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

    @Test
    void shouldGetAthletesPaginated() throws Exception {

        Athlete athlete = Athlete.create(
                "Andres",
                "Alfaro",
                "andres@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        PageResult<Athlete> pageResult = new PageResult<>(
                List.of(athlete),
                0,
                20,
                1,
                1
        );

        PagedAthleteResponse response = new PagedAthleteResponse(
                List.of(
                        new AthleteResponse(
                                athlete.getId(),
                                athlete.getFirstName(),
                                athlete.getLastName(),
                                athlete.getEmail(),
                                athlete.getBirthDate(),
                                athlete.getGender(),
                                athlete.isActive()
                        )
                ),
                0,
                20,
                1,
                1
        );

        when(getAthletesUseCase.getAll(new PageQuery(0, 20)))
                .thenReturn(pageResult);

        when(mapper.toPagedResponse(pageResult))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/athletes")
                                .param("page", "0")
                                .param("size", "20")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Andres"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldUpdateAthlete() throws Exception {

        UUID athleteId = UUID.randomUUID();

        UpdateAthleteCommand command = new UpdateAthleteCommand(
                "Andres",
                "Alfaro",
                "new@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        Athlete updatedAthlete = Athlete.restore(
                athleteId,
                "Andres",
                "Alfaro",
                "new@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE,
                true
        );

        AthleteResponse response = new AthleteResponse(
                athleteId,
                "Andres",
                "Alfaro",
                "new@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE,
                true
        );

        when(mapper.toCommand(any(UpdateAthleteRequest.class)))
                .thenReturn(command);

        when(updateAthleteUseCase.update(athleteId, command))
                .thenReturn(updatedAthlete);

        when(mapper.toResponse(updatedAthlete))
                .thenReturn(response);

        mockMvc.perform(
                        put("/api/v1/athletes/{athleteId}", athleteId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "firstName": "Andres",
                              "lastName": "Alfaro",
                              "email": "new@example.com",
                              "birthDate": "1985-05-20",
                              "gender": "MALE"
                            }
                            """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(athleteId.toString()))
                .andExpect(jsonPath("$.firstName").value("Andres"))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.active").value(true));
    }
}