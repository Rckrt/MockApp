package com.sessionmock.SessionMock.exceptions;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PreviousRequestNotExist extends Exception{


    public PreviousRequestNotExist(HttpServletRequest request) {
        super("Previous request not found for " + request.getRequestURI()
                + " with method " + request.getMethod());
    }

    public PreviousRequestNotExist(RequestPattern requestPattern) {
        super("Previous request not found for " + requestPattern.getUrlPattern()
                + " with method " + requestPattern.getRequestMethod()
                + ". Pattern nickname: " + requestPattern.getNickname());
    }
}
