package com.sessionmock.SessionMock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private HttpStatus status;
    private HashMap<String, List<String>> headerMap;
}
