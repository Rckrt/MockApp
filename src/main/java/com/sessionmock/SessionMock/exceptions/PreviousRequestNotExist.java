package com.sessionmock.SessionMock.exceptions;

import javax.servlet.http.HttpServletRequest;

public class PreviousRequestNotExist extends Exception{

    public PreviousRequestNotExist(String message) {
        super(message);
    }

    public PreviousRequestNotExist(HttpServletRequest request) {
        super("Previous request not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }

    public PreviousRequestNotExist() {
        super();
    }

    public PreviousRequestNotExist(Throwable cause) {
        super(cause);
    }

    public PreviousRequestNotExist(String message, Throwable cause) {
        super(message, cause);
    }
}
