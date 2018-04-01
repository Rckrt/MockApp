package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.model.RequestPattern;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ResponseService {

    Object findResponse(HttpServletRequest request, RequestPattern requestPattern);

}
