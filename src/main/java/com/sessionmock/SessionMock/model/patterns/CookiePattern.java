package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class CookiePattern extends Pattern {
    public CookiePattern() {
        patternType = PatternType.COOKIE;
    }

    @Override
    public void isMatches(HttpServletRequest request) {}
}
