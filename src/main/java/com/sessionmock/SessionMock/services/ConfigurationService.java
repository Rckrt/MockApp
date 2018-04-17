package com.sessionmock.SessionMock.services;

import org.springframework.stereotype.Service;

@Service
public interface ConfigurationService {

    void restart();

    void setPrefix(String prefix);
}
