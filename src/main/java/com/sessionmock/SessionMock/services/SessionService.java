package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Service
public interface SessionService {

    void addToSession(RequestPattern requestPattern, HttpServletRequest request, String body)
            throws  DefaultDataNotFound, PreviousRequestNotExist;


    Map<String, String> getResponseParamMap(RequestPattern requestPattern, HttpServletRequest request) throws InvalidScriptParameters, IOException;
}
