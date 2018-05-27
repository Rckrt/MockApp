package com.sessionmock.SessionMock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public class RequestPatternNotFoundException extends Exception  {

    public RequestPatternNotFoundException(HttpServletRequest request) {
        super("Request pattern not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }

    public RequestPatternNotFoundException(String message) {
        super(message);
    }
}
