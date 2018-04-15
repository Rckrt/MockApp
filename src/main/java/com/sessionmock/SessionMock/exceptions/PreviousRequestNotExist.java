package com.sessionmock.SessionMock.exceptions;

import javax.servlet.http.HttpServletRequest;

public class PreviousRequestNotExist extends Exception{


    public PreviousRequestNotExist(HttpServletRequest request) {
        super("Previous request not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }
}
