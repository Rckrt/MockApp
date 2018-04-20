package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.response.Response;
import com.sessionmock.SessionMock.model.response.TemplateResponse;
import com.sessionmock.SessionMock.services.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
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
    public Object execute(HttpServletRequest request, String body) throws RequestPatternNotFoundException, IOException,
            UrlNotFoundException, PreviousRequestNotExist, DefaultDataNotFound, PatternValidationException, InvalidScriptParameters {
        log.info("Start execute request {} with body {}", request, body);
        RequestPattern requestPattern = requestMappingService.findRequestPattern(request);
        validationService.validateRequest(request, requestPattern, body);
        sessionService.addToSession(requestPattern, request, body);
        return buildResponseEntity(requestPattern, request);
    }

    private ResponseEntity buildResponseEntity(RequestPattern requestPattern , HttpServletRequest request)
            throws IOException, InvalidScriptParameters {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        Response response = requestPattern.getResponse();
        headerMap.putAll(response.getHeaderMap());
        String body = requestPattern.buildBody(request);
        return new ResponseEntity<>(body, headerMap, response.getStatus());
    }
}


