package com.sessionmock.SessionMock.model.response;

import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TextResponse extends Response{

    private String body;

    @Override
    public String getBody(HttpServletRequest request, RequestPattern requestPattern) throws IOException, InvalidScriptParameters {
        return body;
    }
}
