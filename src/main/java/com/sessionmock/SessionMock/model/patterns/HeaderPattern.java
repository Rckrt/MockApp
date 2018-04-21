package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeaderPattern extends Pattern {

    private final static PatternType patternType = PatternType.HEADER;

    @Override
    public boolean isMatches(HttpServletRequest request) throws PatternValidationException {
        return getPatternValue(request).matches(value);
    }

    @Override
    public String getPatternValue(HttpServletRequest request) {
        return request.getHeader(name);
    }

    @Override
    public String buildScriptIdentifier() {
        return "header:" + name;
    }
}
