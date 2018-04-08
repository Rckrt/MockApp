package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

@Service
public interface ConfigurationService {

    void restoreToDefault();

    void restoreToDefault(RequestPattern requestPattern);

    void saveState();
}
