package com.sessionmock.SessionMock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Document(collection = "sessiondata")
@Data
public class SessionData{
    @Id
    private ObjectId _id;
    private String urlPattern;
    @JsonIgnore
    private Map<Pattern, String> sessionAttributeValues;
    private Response response;
    private BasicDBObject data;

    public SessionData(String urlPattern, Map<Pattern, String> sessionAttributeValues) {}

    //TODO: change logic if headers are added
    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(data, response.getStatus());
    }
}