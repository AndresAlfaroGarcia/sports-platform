package com.sportsplatform.athlete.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AthleteJpaRepository
        extends JpaRepository<AthleteEntity, UUID> {
}