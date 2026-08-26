package com.sportsplatform.athlete.application.service;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.command.UpdateAthleteCommand;
import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
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

    @Test
    void shouldGetAthletesPaginated() {

        PageQuery query = new PageQuery(0, 20);

        Athlete athlete = Athlete.create(
                "Andres",
                "Alfaro",
                "andres@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        PageResult<Athlete> expected =
                new PageResult<>(
                        List.of(athlete),
                        0,
                        20,
                        1,
                        1
                );

        when(repository.findAll(query))
                .thenReturn(expected);

        PageResult<Athlete> result =
                service.getAll(query);

        assertEquals(1, result.content().size());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(repository, times(1))
                .findAll(query);
    }

    @Test
    void shouldUpdateAthlete() {

        UUID athleteId = UUID.randomUUID();

        Athlete existingAthlete = Athlete.restore(
                athleteId,
                "Andres",
                "Alfaro",
                "old@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE,
                true
        );

        UpdateAthleteCommand command = new UpdateAthleteCommand(
                "Andres",
                "Alfaro",
                "new@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        when(repository.findById(athleteId))
                .thenReturn(Optional.of(existingAthlete));

        when(repository.save(any(Athlete.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Athlete result = service.update(athleteId, command);

        assertEquals(athleteId, result.getId());
        assertEquals("new@example.com", result.getEmail());

        verify(repository, times(1)).findById(athleteId);
        verify(repository, times(1)).save(existingAthlete);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingAthlete() {

        UUID athleteId = UUID.randomUUID();

        UpdateAthleteCommand command = new UpdateAthleteCommand(
                "Andres",
                "Alfaro",
                "new@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        when(repository.findById(athleteId))
                .thenReturn(Optional.empty());

        assertThrows(
                AthleteNotFoundException.class,
                () -> service.update(athleteId, command)
        );

        verify(repository, times(1)).findById(athleteId);
        verify(repository, never()).save(any(Athlete.class));
    }

    @Test
    void shouldDeactivateAthlete() {

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

        when(repository.save(any(Athlete.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.deactivate(athleteId);

        assertFalse(athlete.isActive());

        verify(repository).findById(athleteId);
        verify(repository).save(athlete);
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingNonExistingAthlete() {

        UUID athleteId = UUID.randomUUID();

        when(repository.findById(athleteId))
                .thenReturn(Optional.empty());

        assertThrows(
                AthleteNotFoundException.class,
                () -> service.deactivate(athleteId)
        );

        verify(repository).findById(athleteId);
        verify(repository, never()).save(any(Athlete.class));
    }
}