package com.sportsplatform.athlete.infrastructure.adapter.out.persistence;

import com.sportsplatform.athlete.domain.model.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "athletes")
public class AthleteEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private boolean active;

    protected AthleteEntity() {
        // Required by JPA
    }

    public AthleteEntity(
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