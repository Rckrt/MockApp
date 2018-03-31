package com.sessionmock.SessionMock.services;

import jdk.nashorn.internal.ir.ObjectNode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestMappingService {

    String findStringResponse(HttpServletRequest request);

    ObjectNode findObjectResponse(HttpServletRequest request);
}
