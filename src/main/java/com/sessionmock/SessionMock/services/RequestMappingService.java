package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface RequestMappingService {

    RequestPattern findRequestPattern(HttpServletRequest request);

    List<RequestPattern> getInputRequestPatterns(RequestPattern requestPattern);

}
