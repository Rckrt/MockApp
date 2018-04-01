package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.Endpoint;
import org.springframework.stereotype.Service;

@Service
public interface ConfigurationService {

    void restoreToDefault();

    void restoreToDefault(Endpoint endpoint);

    void saveState();
}
