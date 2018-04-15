package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public abstract class Pattern {
    protected PatternType patternType;
    protected String name;
    protected boolean isIdentifier;
    protected String valueRegex;

    public abstract void isMatches(HttpServletRequest request) throws PatternValidationException;
}
