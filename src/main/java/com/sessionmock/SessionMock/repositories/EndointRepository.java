package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.Endpoint;
import org.springframework.data.repository.CrudRepository;

public interface EndointRepository extends CrudRepository<Endpoint, String> {
}
