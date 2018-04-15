package com.sessionmock.SessionMock.exceptions;

import javax.servlet.http.HttpServletRequest;

public class RequestPatternNotFoundException extends Exception  {


    public RequestPatternNotFoundException(HttpServletRequest request) {
        super("Request pattern not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }
}
