package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.exceptions.UrlNotFoundException;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface RequestMappingService {

    RequestPattern findRequestPattern(HttpServletRequest request) throws RequestPatternNotFoundException, UrlNotFoundException;

    List<RequestPattern> getInputRequestPatterns(RequestPattern requestPattern);

}
