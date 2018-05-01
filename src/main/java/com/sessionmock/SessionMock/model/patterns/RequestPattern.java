package com.sessionmock.SessionMock.model.patterns;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.model.response.Response;
import lombok.Data;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sessionmock.SessionMock.model.enums.RequestType;

import lombok.EqualsAndHashCode;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;

@Data
@EqualsAndHashCode(of = "nickname")
public class RequestPattern {

    private String urlPattern;
    private String nickname;
    private RequestType requestMethod;
    private List<Pattern> allPatterns;
    private Map<String, List<String>> validateLinks;
    private String validateScript;
    private Response response;
    private JsonNode schema;
    private boolean isInitial = false;

    public List<Pattern> getIdentifierPatterns() {
        return allPatterns.stream()
                .filter(Pattern::isIdentifier)
                .collect(Collectors.toList());
    }

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
    public boolean isContainsIdentifier(Collection<Pattern> patterns){
        return allPatterns.containsAll(patterns);
    }

    public String buildBody(HttpServletRequest request) throws IOException, InvalidScriptParameters {
        return response.getBody(request, this);
    }
}
