package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ValidationService {

    void validateRequest(HttpServletRequest request, RequestPattern requestPattern)
        throws IOException;
}
