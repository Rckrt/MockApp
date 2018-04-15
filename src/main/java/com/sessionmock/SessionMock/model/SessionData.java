package com.sessionmock.SessionMock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Document(collection = "sessiondata")
@Data
@NoArgsConstructor
public class SessionData{
    @Id
    private ObjectId _id;
    private String urlPattern;
    @JsonIgnore
    private List<String> patternValues;
    @JsonIgnore
    private List<Pattern> patterns;
    private Response response;
    private BasicDBObject data;

    //TODO: change logic if headers are added
    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(data, response.getStatus());
    }

    public SessionData(SessionData sessionData) {
        this.urlPattern = sessionData.getUrlPattern();
        this.data = sessionData.getData();
        this.response = sessionData.getResponse();
        this.patterns = sessionData.getPatterns();
        this.patternValues = sessionData.getPatternValues();
    }

    public void addSessionAttributeValues(List<Pattern> patterns, List<String> patternValues) {
        this.patterns = patterns;
        this.patternValues = patternValues;
    }
}
