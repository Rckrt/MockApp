package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface SessionService {

    SessionData findResponse(RequestPattern requestPattern, HttpServletRequest request, String body) throws CloneNotSupportedException, DefaultDataNotFound, PreviousRequestNotExist;
}
