package com.sessionmock.SessionMock.model.patterns;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CookiePattern extends Pattern {
    private String domain;
    private String path;

    public CookiePattern(String name, String value, boolean isInitial, String domain, String path ){
        super(name, value, isInitial);
        this.domain = domain;
        this.path = path;
    }

    public CookiePattern(String name, String value){
        super(name, value, false);
    }

    @Override
    public boolean isMatches(HttpServletRequest request) {
       return Arrays.stream(request.getCookies()).anyMatch(this::isCookieMatchPattern);
    }


    private Cookie getCookie(HttpServletRequest request){
        return Arrays.stream(request.getCookies()).filter(this::isCookieMatchPattern).findFirst().get();
    }

    private boolean isCookieMatchPattern(Cookie cookie){
        return name.matches(cookie.getName())
                && value.matches(cookie.getValue())
                && (domain == null ? cookie.getDomain() == null : domain.matches(cookie.getDomain()))
                && (path == null ? cookie.getPath() == null : path.matches(cookie.getPath()));
    }

    @Override
    public String getPatternValue(HttpServletRequest request) {
        return getCookie(request).getValue();
    }

    @Override
    public String buildScriptIdentifier() {
        return "cookie:" + name;
    }
}
