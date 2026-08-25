package com.sportsplatform.athlete.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Athlete {

    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Gender gender;
    private boolean active;

    private Athlete(
            UUID id,
            String firstName,
            String lastName,
            String email,
            LocalDate birthDate,
            Gender gender,
            boolean active) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.active = active;
    }

    public static Athlete create(
            String firstName,
            String lastName,
            String email,
            LocalDate birthDate,
            Gender gender) {

        return new Athlete(
                UUID.randomUUID(),
                firstName,
                lastName,
                email,
                birthDate,
                gender,
                true
        );
    }

    public static Athlete restore(
            UUID id,
            String firstName,
            String lastName,
            String email,
            LocalDate birthDate,
            Gender gender,
            boolean active) {

        return new Athlete(
                id,
                firstName,
                lastName,
                email,
                birthDate,
                gender,
                active
        );
    }

    public void deactivate() {
        this.active = false;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isActive() {
        return active;
    }
}