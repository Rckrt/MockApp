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

    @Override
    public boolean isMatches(HttpServletRequest request) {
       return Arrays.stream(request.getCookies()).anyMatch(this::isCookieMatchPattern);
    }


    private Cookie getCookie(HttpServletRequest request){
        return Arrays.stream(request.getCookies()).filter(this::isCookieMatchPattern).findFirst().get();
    }

    private boolean isCookieMatchPattern(Cookie cookie){
        return name.equals(cookie.getName()) && cookie.getValue().matches(value)
                && cookie.getDomain().matches(domain) && cookie.getPath().matches(path);
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
