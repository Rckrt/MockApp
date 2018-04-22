package com.sessionmock.SessionMock.model.patterns;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AttributePattern.class, name = "attr"),
        @JsonSubTypes.Type(value = HeaderPattern.class, name = "header"),
        @JsonSubTypes.Type(value = CookiePattern.class, name = "cookie")
})
public abstract class Pattern {
    @Getter
    protected String name;
    @Getter
    protected String value;
    @Getter
    protected boolean identifier;

    public abstract boolean isMatches(HttpServletRequest request) throws PatternValidationException;

    @NonNull
    public abstract String getPatternValue(HttpServletRequest request);

    public abstract String buildScriptIdentifier();
}
