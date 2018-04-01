package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ValidationService {

    void validateRequest(HttpServletRequest request, RequestPattern requestPattern);

    void validateRequestBody(HttpServletRequest request, RequestPattern requestPattern);

}
