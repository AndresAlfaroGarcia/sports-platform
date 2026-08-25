package com.sportsplatform.athlete.application.service;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AthleteApplicationServiceTest {

    @Test
    void shouldCreateAthlete() {

        // Arrange
        AthleteRepositoryPort repository = mock(AthleteRepositoryPort.class);

        AthleteApplicationService service =
                new AthleteApplicationService(repository);

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
}