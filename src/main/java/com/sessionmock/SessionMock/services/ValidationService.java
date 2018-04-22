package com.sessionmock.SessionMock.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import java.io.IOException;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ValidationService {

    void validateRequest(HttpServletRequest request, RequestPattern requestPattern, Object body)
            throws PatternValidationException, JsonProcessingException;
}
