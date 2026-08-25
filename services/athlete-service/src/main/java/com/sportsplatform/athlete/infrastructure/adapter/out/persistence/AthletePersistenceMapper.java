package com.sportsplatform.athlete.infrastructure.adapter.out.persistence;

import com.sportsplatform.athlete.domain.model.Athlete;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface AthletePersistenceMapper {

    //Any class implements this interface inherit this implementation.
    default AthleteEntity toEntity(Athlete athlete) {
        return new AthleteEntity(
                athlete.getId(),
                athlete.getFirstName(),
                athlete.getLastName(),
                athlete.getEmail(),
                athlete.getBirthDate(),
                athlete.getGender(),
                athlete.isActive()
        );
    }

    //Any class implements this interface inherit this implementation.
    default Athlete toDomain(AthleteEntity entity) {
        return Athlete.restore(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.isActive()
        );
    }
}