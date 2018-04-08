package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface SessionService {

    SessionData findSessionData(RequestPattern requestPattern, HttpServletRequest request) throws IOException;

    SessionData saveRequest(RequestPattern requestPattern, HttpServletRequest request);
}
