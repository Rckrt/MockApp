package com.sessionmock.SessionMock.model.patterns;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sessionmock.SessionMock.model.enums.RequestType;

import org.json.JSONObject;

@Data
public class RequestPattern {

    private String urlPattern;
    private String nickname;
    private RequestType requestMethod;
    private List<Pattern> allPatterns;
    private JSONObject schema;
    private boolean isInitial = false;

    public List<Pattern> getIdentifierPatterns() {
        return allPatterns.stream()
                .filter(Pattern::isIdentifier)
                .collect(Collectors.toList());
    }
    //TODO: maybe logic must be changed (equality)
    public boolean isContainsIdentifier(List<Pattern> patterns){
        return allPatterns.containsAll(patterns);
    }
}
