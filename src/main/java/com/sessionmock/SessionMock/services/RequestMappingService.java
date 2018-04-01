package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestMappingService {

    RequestPattern findRequestPattern(HttpServletRequest request);

    String getKeyForUrl(String url);


}
