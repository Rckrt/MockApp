package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.ValidationService;
import java.io.IOException;
import java.util.stream.Stream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern, Object body)
            throws IOException, PatternValidationException {
       validateRequestParameters(request, requestPattern);
       validateRequestBody(body, requestPattern);
    }

    private void validateRequestBody(Object body, RequestPattern requestPattern)
        throws IOException {
        if (requestPattern.getSchema() == null) return;
        Schema schema = SchemaLoader.load(requestPattern.getSchema());
        schema.validate(body);
    }

    private void validateRequestParameters(HttpServletRequest request, RequestPattern requestPattern)
            throws PatternValidationException {
        for (Pattern pattern :requestPattern.getAllPatterns()) {
            pattern.isMatches(request);
        }
    }
}
