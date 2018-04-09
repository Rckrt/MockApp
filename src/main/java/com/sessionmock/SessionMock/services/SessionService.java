package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface SessionService {

    SessionData findResponse(RequestPattern requestPattern, HttpServletRequest request, Object body) throws IOException;
}
