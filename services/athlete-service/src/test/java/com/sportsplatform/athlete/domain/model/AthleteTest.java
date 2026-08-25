package com.sportsplatform.athlete.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AthleteTest {

    @Test
    void shouldDeactivateAthlete() {

        Athlete athlete = Athlete.create(
                "Andres",
                "Alfaro",
                "andres@example.com",
                LocalDate.of(1985, 5, 20),
                Gender.MALE
        );

        athlete.deactivate();

        assertFalse(athlete.isActive());
    }
}