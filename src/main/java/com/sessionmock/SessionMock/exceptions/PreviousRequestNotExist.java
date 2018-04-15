package com.sessionmock.SessionMock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PreviousRequestNotExist extends Exception{


    public PreviousRequestNotExist(HttpServletRequest request) {
        super("Previous request not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }
}
