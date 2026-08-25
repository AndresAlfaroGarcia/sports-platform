package com.sportsplatform.athlete.application.service;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AthleteApplicationServiceTest {

    private AthleteRepositoryPort repository;
    private AthleteApplicationService service;

    @BeforeEach
    void setUp() {
        repository = mock(AthleteRepositoryPort.class);

        service = new AthleteApplicationService(repository);
    }

    @Test
    void shouldCreateAthlete() {

        when(repository.save(any(Athlete.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Athlete athlete = service.create(new CreateAthleteCommand(
                "Andres",
                "Alfaro",
                "andres@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE)
        );

        // Assert
        assertNotNull(athlete);
        assertNotNull(athlete.getId());

        assertEquals("Andres", athlete.getFirstName());
        assertEquals("Alfaro", athlete.getLastName());
        assertEquals("andres@example.com", athlete.getEmail());

        assertEquals(
                LocalDate.of(1985, 5, 20),
                athlete.getBirthDate()
        );

        assertTrue(athlete.isActive());

        verify(repository, times(1))
                .save(any(Athlete.class));
    }

    @Test
    void shouldGetAthleteById() {

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

        when(repository.findById(athleteId))
                .thenReturn(Optional.of(athlete));

        Athlete result = service.getById(athleteId);

        assertEquals(athleteId, result.getId());
        assertEquals("Andres", result.getFirstName());

        verify(repository, times(1))
                .findById(athleteId);
    }

    @Test
    void shouldThrowExceptionWhenAthleteDoesNotExist() {

        UUID athleteId = UUID.randomUUID();

        when(repository.findById(athleteId))
                .thenReturn(Optional.empty());

        assertThrows(
                AthleteNotFoundException.class,
                () -> service.getById(athleteId)
        );

        verify(repository, times(1))
                .findById(athleteId);
    }
}