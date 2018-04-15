package com.sessionmock.SessionMock.exceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

public class RequestPatternNotFoundException extends Exception  {

    public RequestPatternNotFoundException(String message) {
        super(message);
    }

    public RequestPatternNotFoundException(HttpServletRequest request) {
        super("Request pattern not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }

    public RequestPatternNotFoundException() {
        super();
    }

    public RequestPatternNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequestPatternNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
