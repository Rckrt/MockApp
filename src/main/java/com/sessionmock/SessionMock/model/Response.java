package com.sessionmock.SessionMock.model;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class Response {
    private HttpStatus status;
}
