package com.sessionmock.SessionMock.model;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sessionmock.SessionMock.model.enums.RequestType;

import org.json.JSONObject;

@Data
public class RequestPattern {

    private String urlPattern;
    private String nickname;
    private RequestType requestMethod;
    private Map<PatternType,List<Pattern>> allPattern = new HashMap<>();
    private JSONObject schema;

    public void addPattern(PatternType patternType, Pattern pattern){
        if (allPattern.get(patternType) == null){
            List<Pattern> patterns = new ArrayList<>();
            patterns.add(pattern);
            allPattern.put(patternType, patterns);
        }
        else allPattern.get(patternType).add(pattern);
    }

    public List<Pattern> getPatternsByPatternType(PatternType patternType){
        return allPattern.get(patternType);
    }

    public Map<PatternType,List<Pattern>> getAllSessionIdentifierPatternsMap() {
        Map<PatternType,List<Pattern>> identifiers = new HashMap<>();
        allPattern
                .forEach((key, value) -> value
                        .stream()
                        .filter(Pattern::isIdentifier)
                        .forEach(a -> identifiers.put(key, value)));
        return identifiers;
    }
}
