package com.sportsplatform.athlete.infrastructure.adapter.out.persistence;

import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.UUID;

public class AthletePersistenceAdapter implements AthleteRepositoryPort {

    private final AthleteJpaRepository repository;
    private final AthletePersistenceMapper mapper;

    public AthletePersistenceAdapter(
            AthleteJpaRepository repository,
            AthletePersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Athlete save(Athlete athlete) {

        AthleteEntity entity = mapper.toEntity(athlete);

        AthleteEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Athlete get(UUID id) {

        AthleteEntity obj = repository.getReferenceById(id);

        return mapper.toDomain(obj);
    }}