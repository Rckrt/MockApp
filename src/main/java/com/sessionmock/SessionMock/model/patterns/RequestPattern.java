package com.sessionmock.SessionMock.model.patterns;

import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.response.Response;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.sessionmock.SessionMock.model.enums.RequestType;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

@Data
public class RequestPattern {

    private String urlPattern;
    private String nickname;
    private RequestType requestMethod;
    private List<Pattern> allPatterns;
    private Response response;
    private JSONObject schema;
    private boolean isInitial = false;

    public List<Pattern> getIdentifierPatterns() {
        return allPatterns.stream()
                .filter(Pattern::isIdentifier)
                .collect(Collectors.toList());
    }

    //TODO: implement logic
    public List<Pattern> getScriptParamPatterns(List<String> scriptParams) throws InvalidScriptParameters {
        List<Pattern> scriptParamPatterns = allPatterns.stream()
                    .filter(pattern ->  scriptParams.contains(pattern.buildScriptIdentifier()))
                    .collect(Collectors.toList());
        if (scriptParams.containsAll(scriptParamPatterns
                        .stream()
                        .map(Pattern::buildScriptIdentifier)
                        .collect(Collectors.toList())))
             return scriptParamPatterns;
        throw new InvalidScriptParameters(scriptParams, scriptParamPatterns);
    }

    //TODO: maybe logic must be changed (equality)
    public boolean isContainsIdentifier(List<Pattern> patterns){
        return allPatterns.containsAll(patterns);
    }

    public String buildBody(HttpServletRequest request) throws IOException, InvalidScriptParameters {
        return response.getBody(request, this);
    }
}
