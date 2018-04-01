package com.sessionmock.SessionMock.model;

import com.mongodb.BasicDBObject;
import lombok.Data;
import org.bson.types.ObjectId;
import org.everit.json.schema.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "sessiondata")
@Data
public class SessionData{
    @Id
    private ObjectId _id;
    private RequestPattern requestPattern;
    private Map<Pattern, String> sessionAttributeValues;
    private BasicDBObject data;
}