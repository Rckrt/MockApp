package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.Endpoint;
import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.RequestMappingService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RequestMappingServiceImpl implements RequestMappingService {

    private Map<RequestPattern, Pair<List<RequestPattern>, List<RequestPattern>>> requestPatternGraph;
    private Set<RequestPattern> requestPatternSet;

    @Override
    public RequestPattern findRequestPattern(HttpServletRequest request) {
        return null;
    }

    @Override
    public Endpoint findEndpoint(RequestPattern requestPattern) {
        return null;
    }

    @Override
    public String getKeyForUrl(String url) {
        return null;
    }

    private void serializeRequestPatterns() {

    }

    @PostConstruct
    private void buildGraph() {
    }
}
