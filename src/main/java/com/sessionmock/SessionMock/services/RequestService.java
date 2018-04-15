package com.sessionmock.SessionMock.services;

import java.io.IOException;

import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.exceptions.UrlNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestService {

    Object execute(HttpServletRequest request, String body) throws IOException, RequestPatternNotFoundException, UrlNotFoundException, CloneNotSupportedException, PreviousRequestNotExist, DefaultDataNotFound;
}
