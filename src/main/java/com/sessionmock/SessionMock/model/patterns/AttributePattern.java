package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;

import javax.servlet.http.HttpServletRequest;

public class AttributePattern extends Pattern{

    public AttributePattern() {
        patternType = PatternType.ATTRIBUTE;
    }


    @Override
    public void isMatches(HttpServletRequest request) throws PatternValidationException {
       if (!request.getHeader(name).matches(valueRegex))
           throw new PatternValidationException(this, request.getHeader(name));
    }
}
