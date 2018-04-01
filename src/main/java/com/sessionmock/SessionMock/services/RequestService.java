package com.sessionmock.SessionMock.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestService {

    Object execute(HttpServletRequest request);
}
