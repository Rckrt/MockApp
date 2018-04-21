package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface SessionService {

    void addToSession(RequestPattern requestPattern, HttpServletRequest request)
            throws PreviousRequestNotExist;

    void clearAllSessionAttributes();
}
