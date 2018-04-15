package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.ValidationService;
import java.io.IOException;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern, Object body)
            throws IOException, PatternValidationException {
        log.info("Request validation started");
       validateRequestParameters(request, requestPattern);
       validateRequestBody(body, requestPattern);
    }

    private void validateRequestBody(Object body, RequestPattern requestPattern)
        throws IOException {
        log.info("Validate request body {} with pattern {}", body, requestPattern);
        if (requestPattern.getSchema() == null) return;
        Schema schema = SchemaLoader.load(requestPattern.getSchema());
        schema.validate(body);
    }

    private void validateRequestParameters(HttpServletRequest request, RequestPattern requestPattern)
            throws PatternValidationException {
        log.info("Validate request {} with pattern {}", request, requestPattern);
        for (Pattern pattern :requestPattern.getAllPatterns()) {
            pattern.isMatches(request);
        }
    }
}
