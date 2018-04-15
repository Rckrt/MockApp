package com.sessionmock.SessionMock.exceptions;

public class UrlNotFoundException extends Exception{

    public UrlNotFoundException(String message) {
        super("Can't find URL which starts with" + message);
    }
}
