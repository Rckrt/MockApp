package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SessionDataRepository extends CrudRepository<SessionData, String> {

    List<SessionData> findAllByUrlPattern(String urlPattern);

    SessionData findByUrlPatternAndPatternsAndPatternValues(String utl, List<Pattern> patterns, List<String> values);


}
