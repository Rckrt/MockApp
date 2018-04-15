package com.sessionmock.SessionMock.exceptions;

public class UrlNotFoundException extends Exception{

    public UrlNotFoundException(String message) {
        super("Can't find URL " + message);
    }

    public UrlNotFoundException() {
        super();
    }

    public UrlNotFoundException(Throwable cause) {
        super(cause);
    }

    public UrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
