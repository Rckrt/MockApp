package com.sessionmock.SessionMock.repositories;

import com.sessionmock.SessionMock.model.DefaultEndpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultEndpointRepository extends CrudRepository<DefaultEndpoint, String> {
}
