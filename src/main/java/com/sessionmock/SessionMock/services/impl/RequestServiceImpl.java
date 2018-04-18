package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.model.Response;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
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

    @Value("${application.static.resources.templates}")
    private String templatePath;

    @Value("${application.static.resources.scripts}")
    private String scriptPath;

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
        return buildResponseEntity(requestPattern, getResponseParamMap(requestPattern, request));
    }

    private ResponseEntity buildResponseEntity(RequestPattern requestPattern , Map<String, Object> params){
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        Response response = requestPattern.getResponse();
        headerMap.putAll(response.getHeaderMap());
        String body = buildBody(response.getTemplate(), params);
        return new ResponseEntity<>(body, headerMap, response.getStatus());
    }

    private String buildBody(String templateFile,  Map<String, Object> params){
        JtwigTemplate template = JtwigTemplate.fileTemplate(templatePath + File.separator + templateFile);
        JtwigModel model = JtwigModel.newModel(params);
        return template.render(model);
    }

    private Map<String, Object> executeScript(String script, List<String> params) throws IOException {
        return (Map<String, Object>) new GroovyShell()
                .parse(new File(scriptPath + File.separator + script))
                .invokeMethod("main", params.toArray());
    }

    private Map<String,Object> getResponseParamMap(RequestPattern requestPattern, HttpServletRequest request)
            throws InvalidScriptParameters, IOException {
        log.info("Start response data retrieving");
        List<String> scriptParams = requestPattern
                .getScriptParamPatterns(requestPattern.getResponse().getScriptParams())
                .stream()
                .map(pattern -> pattern.getPatternValue(request))
                .collect(Collectors.toList());
        return executeScript(requestPattern.getResponse().getScript(), scriptParams);
    }
}


