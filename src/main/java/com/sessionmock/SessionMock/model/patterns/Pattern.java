package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

@Data
public abstract class Pattern {
    protected PatternType patternType;
    protected String name;
    protected boolean isIdentifier;

    public abstract boolean validate(Object o);
}
