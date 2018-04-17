package com.sessionmock.SessionMock.controller;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.services.RequestService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("/")
@Slf4j
public class SingleController {

    private final RequestService requestService;

    @Autowired
    public SingleController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping("**")
    Object getAllRequests(
        HttpServletRequest request,
        @RequestBody(required = false) String body) throws
            RequestPatternNotFoundException, PatternValidationException, PreviousRequestNotExist,
            DefaultDataNotFound, UrlNotFoundException, IOException, InvalidScriptParameters {
        log.info("Get request {} with some body: {}",request, body);
        return requestService.execute(request, body);
    }
}
