package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.UrlResolver;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SerializationService;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class RequestMappingServiceImpl implements RequestMappingService {

    private final Map<RequestPattern, List<RequestPattern>> requestPatternGraph = new HashMap<>();
    private final Map<String, List<RequestPattern>> urlMapping = new HashMap<>();
    private final UrlResolver urlResolver = UrlResolver.ROOT;
    private final SerializationService serializationService;

    @Autowired
    public RequestMappingServiceImpl(
        SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    @Override
    //TODO: throw custom exception
    public RequestPattern findRequestPattern(HttpServletRequest request) {
        return urlMapping
            .get(urlResolver.findUrl(request.getRequestURI()))
            .stream()
            .filter(pattern -> pattern.getRequestMethod().toString().equals(request.getMethod()))
            //TODO: get pattern by cookie/header/parameters
            .findFirst()
            .orElseThrow(NullPointerException::new);
    }

    @Override
    public List<RequestPattern> getInputRequestPatterns(RequestPattern requestPattern){
        return requestPatternGraph.get(requestPattern);
    }


    @PostConstruct
    private void init() {
        for (List<RequestPattern> scenario : serializationService.getScenariosList()) {
            RequestPattern previousPattern = scenario.get(0);
            addRequestPatternGraphVertex(previousPattern, null);
            for (int i = 1; i < scenario.size(); i++) {
                addRequestPatternGraphVertex(scenario.get(i), previousPattern);
                previousPattern = scenario.get(i);
            }
        }
        buildUrlMapping();
    }


    private void buildUrlMapping() {
        requestPatternGraph.keySet().forEach(this::addKeyMappingEntry);
    }

    private void addKeyMappingEntry(RequestPattern requestPattern){
        urlMapping.computeIfAbsent(requestPattern.getUrlPattern(), k -> new ArrayList<>()).add(requestPattern);
    }

    private void addRequestPatternGraphVertex(RequestPattern pattern, RequestPattern previous){
        requestPatternGraph.computeIfAbsent(pattern, k -> new ArrayList<>()).add(previous);
    }
}
