package com.sessionmock.SessionMock.model.response;

import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextResponse extends Response{

    private String body;

    @Override
    public String getBody(HttpServletRequest request, RequestPattern requestPattern) throws IOException, InvalidScriptParameters {
        return body;
    }
}
