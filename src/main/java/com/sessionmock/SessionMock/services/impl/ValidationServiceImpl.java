package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.ValidationService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern) {

    }

    @Override
    public void validateRequestBody(HttpServletRequest request, RequestPattern requestPattern) {

    }
}
