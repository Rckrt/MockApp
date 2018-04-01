package com.sessionmock.SessionMock.controller;


import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.RequestService;
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
        @RequestParam Map<String, String> allRequestParams,
        @RequestHeader Map<String, String> allRequestHeaders)
    {
        log.info("allRequestParams: {}", allRequestParams);
        log.info("allRequestHeaders: {}", allRequestHeaders);
        log.info("request: {}", request);
        return requestService.execute(request);
    }
}
