package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import java.util.List;

public interface SerializationService {
    List<List<RequestPattern>> getScenariosList();

}
