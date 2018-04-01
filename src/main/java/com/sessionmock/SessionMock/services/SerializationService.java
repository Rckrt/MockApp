package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.RequestPattern;

public interface SerializationService {

    RequestPattern serializeAllRequestPatterns();

    void serializeAllDefaultData();
}
