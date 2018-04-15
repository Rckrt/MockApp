package com.sessionmock.SessionMock.model.patterns;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AttributePattern.class, name = "Attribute"),
        @JsonSubTypes.Type(value = HeaderPattern.class, name = "Header"),
        @JsonSubTypes.Type(value = CookiePattern.class, name = "Cookie")
})
public abstract class Pattern {

    protected String name;
    protected boolean identifier;
    protected String valueRegex;

    public abstract void isMatches(HttpServletRequest request) throws PatternValidationException;

    @NonNull
    public abstract String getPatternValue(HttpServletRequest request);
}
