package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestServiceImpl implements RequestService {

    private final ValidationService validationService;
    private final SessionService sessionService;
    private final RequestMappingService requestMappingService;

    public RequestServiceImpl(ValidationService validationService, SessionService sessionService, RequestMappingService requestMappingService) {
        this.validationService = validationService;
        this.sessionService = sessionService;
        this.requestMappingService = requestMappingService;
    }

    @Override
    public Object execute(HttpServletRequest request) {
        RequestPattern requestPattern = requestMappingService.findRequestPattern(request);
        validationService.validateRequest(request, requestPattern);
        return sessionService.findSessionData(requestPattern, request);
    }
}
