package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class HeaderPattern extends Pattern {

    public HeaderPattern() {
        patternType = PatternType.HEADER;
    }


    @Override
    public void isMatches(HttpServletRequest request) throws PatternValidationException {
        if(!request.getHeader(name).matches(valueRegex))
            throw new PatternValidationException(this, request.getHeader(name));
    }
}
