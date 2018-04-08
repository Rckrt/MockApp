package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.ConfigurationService;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Override
    public void restoreToDefault() {

    }

    @Override
    public void restoreToDefault(RequestPattern requestPattern) {

    }

    @Override
    public void saveState() {

    }
}
