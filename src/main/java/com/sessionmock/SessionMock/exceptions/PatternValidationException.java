package com.sessionmock.SessionMock.exceptions;

import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PatternValidationException extends Exception{

    public PatternValidationException(Pattern pattern, HttpServletRequest request){
        super(pattern.getPatternValue(request) + " doesn't match pattern "
                + pattern.getName() + " with pattern type " + pattern.getClass().getName() );
    }

    public PatternValidationException(RequestPattern requestPattern, HttpServletRequest request) {
        super(request.getMethod() + request.getRequestURI() + " doesn't match pattern "
                + requestPattern + ": validation script return false" );
    }
}
