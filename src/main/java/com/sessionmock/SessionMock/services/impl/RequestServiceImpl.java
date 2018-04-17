package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.model.Response;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.*;
import java.io.IOException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
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
        return buildResponseEntity(requestPattern.getResponse(), sessionService.getResponseParamMap(requestPattern, request));
    }

    private ResponseEntity buildResponseEntity(Response response, Map<String, String> params){
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.putAll(response.getHeaderMap());

        return new ResponseEntity<>(buildBody(response.getBody(), params), headerMap, response.getStatus());
    }

    //TODO: implement logic
    private Object buildBody(Object data,  Map<String, String> params){
        return data;
    }
}
