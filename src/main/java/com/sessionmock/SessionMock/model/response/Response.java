package com.sessionmock.SessionMock.model.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TemplateResponse.class, name = "template")
})
public abstract class Response {
    private HttpStatus status;
    private HashMap<String, List<String>> headerMap;

    public abstract String getBody(HttpServletRequest request);
}
