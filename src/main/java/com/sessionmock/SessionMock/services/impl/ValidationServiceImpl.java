package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.model.enums.PatternType;
import com.sessionmock.SessionMock.services.ValidationService;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateRequest(HttpServletRequest request, RequestPattern requestPattern)
        throws IOException {
       validateRequestParameters(request, requestPattern);
       validateRequestBody(request, requestPattern);
    }

    private void validateRequestBody(HttpServletRequest request, RequestPattern requestPattern)
        throws IOException {
        if (requestPattern.getSchema() == null) return;
        Schema schema = SchemaLoader.load(requestPattern.getSchema());
        schema.validate(IOUtils.toString(request.getReader()));
    }

    private void validateRequestParameters(HttpServletRequest request, RequestPattern requestPattern)
        throws IOException {

    }
}
