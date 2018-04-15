package com.sessionmock.SessionMock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DefaultDataNotFound extends Exception{

    public DefaultDataNotFound(String url) {
        super("Default data not found for URL " + url);
    }

}
