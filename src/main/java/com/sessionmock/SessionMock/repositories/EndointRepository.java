package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndointRepository extends CrudRepository<Endpoint, String> {
}
