package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.SessionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDataRepository extends CrudRepository<SessionData, String> {
}
