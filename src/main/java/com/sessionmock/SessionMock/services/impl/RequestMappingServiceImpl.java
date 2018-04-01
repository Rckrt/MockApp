package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.Endpoint;
import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.RequestMappingService;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class RequestMappingServiceImpl implements RequestMappingService {

    private Map<RequestPattern, List<RequestPattern>> requestPatternGraph;

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

    @PostConstruct
    private void buildGraph() {
    }
}
