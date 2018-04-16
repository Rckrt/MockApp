package com.sessionmock.SessionMock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Data
@NoArgsConstructor
public class SessionData{
    private String urlPattern;
    @JsonIgnore
    private List<String> patternValues;
    @JsonIgnore
    private List<Pattern> patterns;
    private Response response;
    private Object data;

    //TODO: change logic if headers are added
    public ResponseEntity getResponseEntity(){
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.putAll(response.getHeaderMap());
        return new ResponseEntity<>(data, headerMap, response.getStatus());
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
