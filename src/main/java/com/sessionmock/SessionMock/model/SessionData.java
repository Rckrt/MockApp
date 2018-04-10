package com.sessionmock.SessionMock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Document(collection = "sessiondata")
@Data
@NoArgsConstructor
public class SessionData{
    @Id
    private ObjectId _id;
    private String urlPattern;
    @JsonIgnore
    private Map<Pattern, String> sessionAttributeValues;
    private Response response;
    private BasicDBObject data;

    //TODO: change logic if headers are added
    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(data, response.getStatus());
    }

    public SessionData clone() throws CloneNotSupportedException{
        SessionData obj=(SessionData)super.clone();
        obj.urlPattern = urlPattern;
        obj.response = response;
        obj.sessionAttributeValues = sessionAttributeValues;
        obj.data = data;
        return obj;
    }
}