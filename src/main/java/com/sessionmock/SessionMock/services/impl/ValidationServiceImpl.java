package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.ValidationService;
import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern, Object body)
        throws IOException {
       validateRequestParameters(request, requestPattern);
       validateRequestBody(body, requestPattern);
    }

    private void validateRequestBody(Object body, RequestPattern requestPattern)
        throws IOException {
        if (requestPattern.getSchema() == null) return;
        Schema schema = SchemaLoader.load(requestPattern.getSchema());
        schema.validate(body);
    }

    //TODO: implement logic
    private void validateRequestParameters(HttpServletRequest request, RequestPattern requestPattern) {

    }
}
