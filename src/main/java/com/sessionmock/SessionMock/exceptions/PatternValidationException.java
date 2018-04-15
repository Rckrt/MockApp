package com.sessionmock.SessionMock.exceptions;

import com.sessionmock.SessionMock.model.patterns.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PatternValidationException extends Exception{

    public PatternValidationException(Pattern pattern, String value){
        super("Value " + value + "doesn't match" + pattern.getPatternType() + " pattern "
                + pattern.getName() + " " + pattern.getValueRegex());
    }
}
