package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.enums.PatternType;
import com.sessionmock.SessionMock.repositories.SessionDataRepository;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SessionService;
import com.sessionmock.SessionMock.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private final SessionDataRepository sessionDataRepository;
    private final ValidationService validationService;

    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService, SessionDataRepository sessionDataRepository,
                              ValidationService validationService) {
        this.requestMappingService = requestMappingService;
        this.sessionDataRepository = sessionDataRepository;
        this.validationService = validationService;
    }


    private boolean isRelateToScenario(RequestPattern requestPattern, HttpServletRequest request) {
        if (!requestPattern.isInitial()) {
            List<Pattern> patternsList = requestPattern.getIdentifierPatterns();
            List<RequestPattern> identityRequestPatterns = requestMappingService
                    .getInputRequestPatterns(requestPattern).stream()
                    .filter(pattern -> pattern.isContainsIdentifier(patternsList))
                    .collect(Collectors.toList());
            return validationService.isPreviousExist(request,
                    sessionDataRepository.findAllByRequestPatternIn(identityRequestPatterns));
        }
        return true;
    }

    @Override
    //TODO: throw custom exception
    public SessionData findSessionData(RequestPattern requestPattern, HttpServletRequest request) throws IOException {
        isRelateToScenario(requestPattern, request);
        return null;
    }

    @Override
    public SessionData saveRequest(RequestPattern requestPattern, HttpServletRequest request){
        return sessionDataRepository.save(new SessionData(requestPattern, request));
    }
}
