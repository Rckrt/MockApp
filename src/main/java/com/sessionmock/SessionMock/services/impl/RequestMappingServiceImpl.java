package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.Endpoint;
import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.RequestMappingService;
import jdk.nashorn.internal.ir.ObjectNode;

import javax.servlet.http.HttpServletRequest;

public class RequestMappingServiceImpl implements RequestMappingService {

    @Override
    public String findStringResponse(HttpServletRequest request) {
        return null;
    }

    @Override
    public ObjectNode findObjectResponse(HttpServletRequest request) {
        return null;
    }


    @Override
    public RequestPattern findRequestPattern(HttpServletRequest request) {
        return null;
    }

    @Override
    public Endpoint findEndpoint(RequestPattern requestPattern) {
        return null;
    }
}
