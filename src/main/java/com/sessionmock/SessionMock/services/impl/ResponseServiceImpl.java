package com.sessionmock.SessionMock.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.ResponseService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public Object findResponse(HttpServletRequest request, RequestPattern requestPattern) {
        return null;
    }
}
