package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttributePattern extends Pattern{

    public AttributePattern(String name, String value, boolean isInitial){
        super(name, value, isInitial);
    }

    @Override
    public boolean isMatches(HttpServletRequest request) throws PatternValidationException {
       return getPatternValue(request).matches(value);
    }

    @Override
    public String getPatternValue(HttpServletRequest request) {
        return request.getParameter(name);
    }

    @Override
    public String buildScriptIdentifier() {
        return "attr:" + name ;
    }
}
