package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.Pattern;
import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.enums.PatternType;
import com.sessionmock.SessionMock.repositories.SessionDataRepository;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private final SessionDataRepository sessionDataRepository;

    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService, SessionDataRepository sessionDataRepository) {
        this.requestMappingService = requestMappingService;
        this.sessionDataRepository = sessionDataRepository;
    }


    private boolean isPreviousRequestExist(RequestPattern requestPattern, HttpServletRequest request) {
        return true;
        //requestMappingService.getInputRequestPatterns(requestPattern);
    }

    private boolean isInitialRequest(RequestPattern requestPattern){
        return false;
    }

    @Override
    public SessionData findSessionData(RequestPattern requestPattern, HttpServletRequest request) {
        requestPattern.getAllSessionIdentifierPatternsMap().values();
        sessionDataRepository
                .findAllByRequestPatternIn(requestMappingService.getInputRequestPatterns(requestPattern));

        return null;
    }

    @Override
    public SessionData saveRequest(RequestPattern requestPattern, HttpServletRequest request){
        return sessionDataRepository.save(new SessionData(requestPattern, request));
    }

    private List<String> returnValuesByPatternType(PatternType patternType){
        return null;
    }
}
