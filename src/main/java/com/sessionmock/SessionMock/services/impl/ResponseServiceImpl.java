package com.sessionmock.SessionMock.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sessionmock.SessionMock.services.ResponseService;

import javax.servlet.http.HttpServletRequest;

public class ResponseServiceImpl implements ResponseService {
    @Override
    public String findStringResponse(HttpServletRequest request) {
        return null;
    }

    @Override
    public ObjectNode findObjectResponse(HttpServletRequest request) {
        return null;
    }
}
