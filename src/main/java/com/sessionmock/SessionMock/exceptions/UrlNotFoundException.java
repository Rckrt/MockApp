package com.sessionmock.SessionMock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UrlNotFoundException extends Exception{

    public UrlNotFoundException(String message) {
        super("Can't find URL which starts with " + message);
    }
}
