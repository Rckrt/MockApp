package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
public class AttributePattern extends Pattern{

    private final PatternType patternType = PatternType.ATTRIBUTE;

    @Override
    public void isMatches(HttpServletRequest request) throws PatternValidationException {
       if (!getPatternValue(request).matches(valueRegex))
           throw new PatternValidationException(this, request.getHeader(name));
    }

    @Override
    public String getPatternValue(HttpServletRequest request) {
        return request.getParameter(name);
    }
}
