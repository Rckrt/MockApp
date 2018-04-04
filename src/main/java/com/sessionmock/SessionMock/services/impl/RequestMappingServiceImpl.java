package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
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

    private Map<RequestPattern, List<RequestPattern>> requestPatternGraph = new HashMap<>();
    private final SerializationService serializationService;

    @Autowired
    public RequestMappingServiceImpl(
        SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    @Override
    public RequestPattern findRequestPattern(HttpServletRequest request) {
        return null;
    }

    @Override
    public List<RequestPattern> getInputRequestPatterns(RequestPattern requestPattern){
        return requestPatternGraph.get(requestPattern);
    }

    @Override
    public String getKeyForUrl(String url) {
        return null;
    }

    @PostConstruct
    private void buildGraph() {
        for (List<RequestPattern> scenario : serializationService.getScenariosList()) {
            RequestPattern previousPattern = scenario.get(0);
            addRequestPatternGraphVertex(previousPattern, null);
            for (int i = 1; i < scenario.size(); i++) {
                addRequestPatternGraphVertex(scenario.get(i), previousPattern);
                previousPattern = scenario.get(i);
            }
        }
    }

    private void addRequestPatternGraphVertex(RequestPattern pattern, RequestPattern previous){
        requestPatternGraph.computeIfAbsent(pattern, k -> new ArrayList<>());
        requestPatternGraph.get(pattern).add(previous);
    }
}
