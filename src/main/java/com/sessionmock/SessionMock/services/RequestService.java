package com.sessionmock.SessionMock.services;

import java.io.IOException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestService {

    Object execute(HttpServletRequest request) throws IOException;
}
