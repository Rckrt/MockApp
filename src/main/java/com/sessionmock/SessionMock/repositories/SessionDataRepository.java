package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionDataRepository extends CrudRepository<SessionData, String> {

    List<SessionData> findAllByUrlPattern(String urlPattern);


}
