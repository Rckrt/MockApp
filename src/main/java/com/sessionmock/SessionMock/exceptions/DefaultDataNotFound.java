package com.sessionmock.SessionMock.exceptions;

public class DefaultDataNotFound extends Exception{

    public DefaultDataNotFound(String url) {
        super("Default data not found for URL " + url);
    }

}
