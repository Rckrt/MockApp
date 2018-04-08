package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;

public class AttributePattern extends Pattern{

    public AttributePattern() {
        patternType = PatternType.ATTRIBUTE;
    }

    @Override
    public boolean validate(Object o) {
        return false;
    }
}
