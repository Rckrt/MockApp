package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

@Data
public class CookiePattern extends Pattern {
    public CookiePattern() {
        patternType = PatternType.COOKIE;
    }

    @Override
    public boolean validate(Object o) {
        return false;
    }
}
