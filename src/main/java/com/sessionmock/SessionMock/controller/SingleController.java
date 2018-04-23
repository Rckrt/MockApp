package com.sessionmock.SessionMock.controller;

import com.sessionmock.SessionMock.exceptions.*;
import com.sessionmock.SessionMock.model.UrlResolver;
import com.sessionmock.SessionMock.services.RequestService;
import com.sessionmock.SessionMock.services.SessionService;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController("/")
@Slf4j
public class SingleController {

    private final RequestService requestService;
    private final SessionService sessionService;

    @Autowired
    public SingleController(RequestService requestService, SessionService sessionService) {
        this.requestService = requestService;
        this.sessionService = sessionService;
    }

    @RequestMapping("**")
    public Object getAllRequests(
        HttpServletRequest request,
        @RequestBody(required = false) String body) throws
            RequestPatternNotFoundException, PatternValidationException, PreviousRequestNotExist,
            UrlNotFoundException, IOException, InvalidScriptParameters {
        log.info("Get request {} with some body: {}",request, body);
        return requestService.execute(request, body);
    }

    @RequestMapping(value = "config/prefix", method = RequestMethod.POST)
    public ResponseEntity setPrefix(
            @RequestBody String prefix){
        UrlResolver.setPrefix(prefix);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "config/clear_all", method = RequestMethod.DELETE)
    public ResponseEntity clearAll(){
        sessionService.clearAllSessionAttributes();
        return new ResponseEntity(HttpStatus.OK);
    }
}
