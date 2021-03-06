package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.response.Response;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class RequestService {

    private final ValidationService validationService;
    private final SessionService sessionService;
    private final RequestMappingService requestMappingService;

    public RequestService(ValidationService validationService, SessionService sessionService, RequestMappingService requestMappingService) {
        this.validationService = validationService;
        this.sessionService = sessionService;
        this.requestMappingService = requestMappingService;
    }

    public Object execute(HttpServletRequest request, String body) throws RequestPatternNotFoundException, IOException,
            UrlNotFoundException, PreviousRequestNotExist, PatternValidationException, InvalidScriptParameters {
        log.info("Start execute request {} with body {}", request, body);
        RequestPattern requestPattern = requestMappingService.findRequestPattern(request);
        validationService.validateRequest(request, requestPattern, body);
        sessionService.addToSession(requestPattern, request);
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


