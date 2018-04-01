package com.sessionmock.SessionMock.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ResponseService {

    String findStringResponse(HttpServletRequest request);

    ObjectNode findObjectResponse(HttpServletRequest request);

}
