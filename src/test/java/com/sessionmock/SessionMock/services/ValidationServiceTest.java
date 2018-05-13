package com.sessionmock.SessionMock.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.model.enums.RequestType;
import com.sessionmock.SessionMock.model.patterns.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ValidationServiceTest {

    private final ValidationService  validationService = new ValidationService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final RequestType REQUEST_METHOD = RequestType.POST;

    private static final int RANDOM_SIZE = 10;

    private static final String URL = "validation/test";

    private static final String ATTRIBUTE_NAME = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);

    private static final String ATTRIBUTE_VALUE = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);

    private static final String HEADER_NAME = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);

    private static final String HEADER_VALUE = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);

    private static final String COOKIE_NAME = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);

    private static final String COOKIE_VALUE = RandomStringUtils.randomAlphanumeric(RANDOM_SIZE);


    @Value("classpath:examples/validation/body.json")
    private Resource body;

    @Value("classpath:examples/validation/not_valid_body.json")
    private Resource notValidBody;

    @Value("classpath:examples/validation/bodySchema.json")
    private Resource bodySchema;

    @Test
    public void testHeaderShouldValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);
        mockRequest.addHeader(HEADER_NAME, HEADER_VALUE);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        Pattern headerPattern = new HeaderPattern(HEADER_NAME, HEADER_VALUE, true);
        requestPattern.setAllPatterns(Collections.singletonList(headerPattern));
        validationService.validateRequest(mockRequest, requestPattern, null);
    }

    @Test
    public void testAttributeShouldValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);
        mockRequest.addParameter(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        Pattern attributePattern = new AttributePattern(ATTRIBUTE_NAME, ATTRIBUTE_VALUE, true);
        requestPattern.setAllPatterns(Collections.singletonList(attributePattern));
        validationService.validateRequest(mockRequest, requestPattern, null);
    }

    @Test
    public void testCookieShouldValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        mockRequest.setCookies(cookie);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        Pattern attributePattern = new CookiePattern(COOKIE_NAME, COOKIE_VALUE, true, null, null);
        requestPattern.setAllPatterns(Collections.singletonList(attributePattern));
        validationService.validateRequest(mockRequest, requestPattern, null);
    }

    @Test
    public void testBodyShouldValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        requestPattern.setSchema(objectMapper.readTree(IOUtils.toByteArray(bodySchema.getInputStream())));

        validationService.validateRequest(mockRequest, requestPattern, IOUtils.toString(body.getInputStream()));
    }

    @Test
    public void testBodyShouldNotValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        requestPattern.setSchema(objectMapper.readTree(IOUtils.toByteArray(bodySchema.getInputStream())));

        try {
            validationService
                    .validateRequest(mockRequest, requestPattern, IOUtils.toString(notValidBody.getInputStream()));
        }
        catch (Throwable throwable){
            assertNotNull(throwable);
        }
    }

    @Test
    public void testComplexRequestShouldValidate() throws IOException, PatternValidationException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(REQUEST_METHOD.name(), URL);
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        mockRequest.addHeader(HEADER_NAME, HEADER_VALUE);
        mockRequest.addParameter(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        mockRequest.setCookies(cookie);

        RequestPattern requestPattern = new RequestPattern();
        requestPattern.setUrlPattern(URL);
        requestPattern.setRequestMethod(REQUEST_METHOD);
        Pattern headerPattern = new HeaderPattern(HEADER_NAME, HEADER_VALUE, true);
        Pattern cookiePattern = new CookiePattern(COOKIE_NAME, COOKIE_VALUE, true, null, null);
        Pattern attributePattern = new AttributePattern(ATTRIBUTE_NAME, ATTRIBUTE_VALUE, true);
        List<Pattern> patterns = new ArrayList<>();
        Collections.addAll(patterns, headerPattern, cookiePattern, attributePattern);
        requestPattern.setAllPatterns(patterns);

        requestPattern.setSchema(objectMapper.readTree(IOUtils.toByteArray(bodySchema.getInputStream())));


        requestPattern.setAllPatterns(Collections.singletonList(headerPattern));

        validationService.validateRequest(mockRequest, requestPattern, IOUtils.toString(body.getInputStream()));
    }




}
