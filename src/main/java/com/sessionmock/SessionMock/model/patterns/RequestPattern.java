package com.sessionmock.SessionMock.model.patterns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sessionmock.SessionMock.model.enums.RequestType;

import org.json.JSONObject;

@Data
public class RequestPattern {

    private String urlPattern;
    private String nickname;
    private RequestType requestMethod;
    private List<Pattern> allPatterns = new ArrayList<>();
    private JSONObject schema;
    private boolean isInitial = false;

    public void addPattern(Pattern pattern){
       allPatterns.add(pattern);
    }

    public List<Pattern> getPatternsByPatternType(PatternType patternType){
        return allPatterns.stream()
                .filter(pattern -> pattern.patternType.equals(patternType))
                .collect(Collectors.toList());
    }

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
