package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.exceptions.UrlNotFoundException;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.UrlResolver;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class RequestMappingService {

    private final Map<RequestPattern, List<RequestPattern>> requestPatternGraph = new HashMap<>();
    private final Map<String, List<RequestPattern>> urlMapping = new HashMap<>();
    private final SerializationService serializationService;

    @Autowired
    public RequestMappingService(
        SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    public RequestPattern findRequestPattern(HttpServletRequest request) throws RequestPatternNotFoundException, UrlNotFoundException {
        log.info("Pattern search began for request {}", request);
        return (UrlResolver.findUrl(request.getRequestURI())).stream()
            .flatMap(url -> urlMapping.get(url).stream())
            .filter(requestPattern -> requestPattern.getRequestMethod().toString().equals(request.getMethod()) &&
                    requestPattern.getAllPatterns()
                            .stream()
                            .noneMatch(pattern -> pattern.getPatternValue(request)==null))
            .findFirst()
            .orElseThrow(() -> new RequestPatternNotFoundException(request));
    }

    public List<RequestPattern> getInputRequestPatterns(RequestPattern requestPattern){
        return requestPatternGraph.get(requestPattern);
    }

    @PostConstruct
    private void init() {
        buildGraph();
        buildUrlMapping();
    }

    private void buildGraph(){
        for (List<Set<RequestPattern>> scenario : serializationService.getScenariosList()) {
            Set<RequestPattern> previousPatterns = scenario.get(0);
            addRequestPatternGraphVertex(previousPatterns, null);

            for (int i = 1; i < scenario.size(); i++) {
                for (RequestPattern previous : previousPatterns) {
                    UrlResolver.addUrl(previous.getUrlPattern());
                    addRequestPatternGraphVertex(scenario.get(i), previous);
                    previousPatterns = scenario.get(i);
                }
            }
        }
    }

    private void buildUrlMapping() {
        requestPatternGraph.keySet().forEach(this::addKeyMappingEntry);
    }

    private void addKeyMappingEntry(RequestPattern requestPattern){
        urlMapping.computeIfAbsent(requestPattern.getUrlPattern(), k -> new ArrayList<>()).add(requestPattern);
    }

    private void addRequestPatternGraphVertex(Set<RequestPattern> patterns, RequestPattern previous){
        for (RequestPattern pattern : patterns) requestPatternGraph
                .computeIfAbsent(pattern, k -> new ArrayList<>()).add(previous);
    }
}
