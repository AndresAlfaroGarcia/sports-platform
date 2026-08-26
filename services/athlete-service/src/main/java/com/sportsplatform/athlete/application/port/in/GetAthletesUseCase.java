package com.sportsplatform.athlete.application.port.in;

import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.domain.model.Athlete;

public interface GetAthletesUseCase {

    PageResult<Athlete> getAll(PageQuery pageQuery);
}