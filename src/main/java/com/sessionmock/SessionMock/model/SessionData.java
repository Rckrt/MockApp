package com.sessionmock.SessionMock.model;

import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.model.enums.PatternType;
import lombok.Data;
import org.bson.types.ObjectId;
import org.everit.json.schema.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "sessiondata")
@Data
public class SessionData{
    @Id
    private ObjectId _id;
    private RequestPattern requestPattern;
    private Map<PatternType, Map<Pattern, String>> sessionAttributeValues;
    private BasicDBObject data;

    public SessionData(RequestPattern requestPattern, HttpServletRequest request) {

    }

    public Map<PatternType, Map<Pattern, String>> getAllSessionIdentifierPatternsMap() {
        Map<PatternType, Map<Pattern, String>> identifiers = new HashMap<>();
        sessionAttributeValues
                .forEach((key, value) -> value
                        .keySet()
                        .stream()
                        .filter(Pattern::isIdentifier)
                        .forEach(a -> identifiers.put(key, value)));
        return identifiers;
    }
}