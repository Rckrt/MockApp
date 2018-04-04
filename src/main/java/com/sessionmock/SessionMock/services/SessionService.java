package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface SessionService {

    SessionData findSessionData(RequestPattern requestPattern, HttpServletRequest request);

    SessionData saveRequest(RequestPattern requestPattern, HttpServletRequest request);
}
