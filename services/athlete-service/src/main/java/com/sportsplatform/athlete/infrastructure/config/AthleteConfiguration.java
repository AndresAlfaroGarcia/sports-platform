package com.sportsplatform.athlete.infrastructure.config;

import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.application.service.AthleteApplicationService;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper.AthleteRestMapper;
import com.sportsplatform.athlete.infrastructure.adapter.out.persistence.AthleteJpaRepository;
import com.sportsplatform.athlete.infrastructure.adapter.out.persistence.AthletePersistenceAdapter;
import com.sportsplatform.athlete.infrastructure.adapter.out.persistence.AthletePersistenceMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AthleteConfiguration {

    @Bean
    AthletePersistenceMapper athletePersistenceMapper() {
        return new AthletePersistenceMapper() {};
    }

    @Bean
    AthleteRepositoryPort athleteRepositoryPort(
            AthleteJpaRepository repository,
            AthletePersistenceMapper mapper) {

        return new AthletePersistenceAdapter(repository, mapper);
    }

    @Bean
    public AthleteRestMapper athleteRestMapper() {
        return new AthleteRestMapper();
    }

    @Bean
    public AthleteApplicationService athleteApplicationService(
            AthleteRepositoryPort repositoryPort) {

        return new AthleteApplicationService(repositoryPort);
    }
}