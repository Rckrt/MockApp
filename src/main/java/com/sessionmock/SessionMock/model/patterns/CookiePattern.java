package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CookiePattern extends Pattern {

    protected final PatternType patternType = PatternType.COOKIE;

    @Override
    public void isMatches(HttpServletRequest request) {}

    @Override
    public String getPatternValue(HttpServletRequest request) {
        return null;
    }
}
