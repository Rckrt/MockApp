package com.sessionmock.SessionMock.model;

import com.mongodb.BasicDBObject;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "runtime")
@Data
public class Endpoint extends DefaultEndpoint {
    @Id
    private ObjectId _id;
    private RequestPattern requestPattern;
    private BasicDBObject data;

}
