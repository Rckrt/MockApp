package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.services.SessionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SessionServiceImpl implements SessionService{

    @Override
    public void isPreviosRequestExist(RequestPattern requestPattern, HttpServletRequest request) {
    }

    @Override
    public SessionData findSessionData(RequestPattern requestPattern, HttpServletRequest request) {
        return null;
    }
}
