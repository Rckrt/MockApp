package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.ValidationService;

import javax.servlet.http.HttpServletRequest;

public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern) {

    }

    @Override
    public void validateRequestBody(HttpServletRequest request, RequestPattern requestPattern) {

    }
}
