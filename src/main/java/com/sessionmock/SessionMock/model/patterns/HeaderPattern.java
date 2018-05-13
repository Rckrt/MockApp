package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeaderPattern extends Pattern {

    public HeaderPattern(String name, String value, boolean isInitial){
        super(name, value, isInitial);
    }

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
