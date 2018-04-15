package com.sessionmock.SessionMock.exceptions;

public class DefaultDataNotFound extends Exception{

    public DefaultDataNotFound(String url) {
        super("Default data not found for URL " + url);
    }

    public DefaultDataNotFound() {
        super();
    }

    public DefaultDataNotFound(Throwable cause) {
        super(cause);
    }

    public DefaultDataNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
