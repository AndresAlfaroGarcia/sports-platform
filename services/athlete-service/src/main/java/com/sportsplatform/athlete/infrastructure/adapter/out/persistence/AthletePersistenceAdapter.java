package com.sportsplatform.athlete.infrastructure.adapter.out.persistence;

import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.model.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
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
    public Optional<Athlete> findById(UUID athleteId) {

        return repository.findById(athleteId)
                .map(mapper::toDomain); //Reference to method
    }

    @Override
    public PageResult<Athlete> findAll(PageQuery pageQuery) {

        PageRequest pageable = PageRequest.of(
                pageQuery.page(),
                pageQuery.size()
        );

        Page<AthleteEntity> page = repository.findAll(pageable);

        List<Athlete> athletes = page.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PageResult<>(
                athletes,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}