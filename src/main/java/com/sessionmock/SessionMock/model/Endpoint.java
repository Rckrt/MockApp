package com.sessionmock.SessionMock.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "runtime")
@Data
public class Endpoint extends DefaultEndpoint {
    private String url;
    private List<String> cookies;
    private List<String> headers;
}
