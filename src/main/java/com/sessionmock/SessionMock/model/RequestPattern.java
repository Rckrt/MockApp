package com.sessionmock.SessionMock.model;

import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;
import org.everit.json.schema.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RequestPattern {

    private String urlPattern;
    private String requestMethod;
    private Map<PatternType,List<Pattern>> allPattern =  new HashMap<>();
    private Schema schema;

    public void addPattern(PatternType patternType, Pattern pattern){
        if (allPattern.get(patternType) == null){
            List<Pattern> patterns = new ArrayList<>();
            patterns.add(pattern);
            allPattern.put(patternType, patterns);
        }
        else allPattern.get(patternType).add(pattern);
    }
}
