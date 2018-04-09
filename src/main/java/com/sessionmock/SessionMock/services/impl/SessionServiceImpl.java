package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.Response;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.repositories.SessionDataRepository;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SessionService;
import com.sessionmock.SessionMock.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private final SessionDataRepository sessionDataRepository;
    private final ValidationService validationService;
    private Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes;

    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService, SessionDataRepository sessionDataRepository,
                              ValidationService validationService) {
        this.requestMappingService = requestMappingService;
        this.sessionDataRepository = sessionDataRepository;
        this.validationService = validationService;
    }


    private List<RequestPattern> getPreviousPatterns(RequestPattern requestPattern, HttpServletRequest request) {
            List<Pattern> patternsList = requestPattern.getIdentifierPatterns();
            return requestMappingService
                    .getInputRequestPatterns(requestPattern).stream()
                    .filter(pattern -> pattern.isContainsIdentifier(patternsList))
                    .collect(Collectors.toList());
    }

    //TODO: implement logic
    private Map<Pattern,String> buildIdentifierMap(List<Pattern> patternList, HttpServletRequest request){
        return null;
    }

    @Override
    //TODO: throw custom exception
    public Response findResponse(RequestPattern requestPattern, HttpServletRequest request) throws IOException {
        Map<Pattern,String> currentIdentifierMap = buildIdentifierMap(requestPattern.getIdentifierPatterns(), request);
        if (!isPreviousRequestExist(requestPattern, request, currentIdentifierMap)) throw new NullPointerException();
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
        return saveRequest(requestPattern.getUrlPattern(), currentIdentifierMap, request).getResponse();
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
    }

    private boolean isPreviousRequestExist(RequestPattern requestPattern, HttpServletRequest request,
                                          Map<Pattern,String> currentIdentifierMap){
        if (!requestPattern.isInitial()) {
            List<RequestPattern> previousRequestPatterns = getPreviousPatterns(requestPattern, request);
            return previousRequestPatterns.stream()
                    .anyMatch(pattern -> sessionAttributes.get(pattern).contains(currentIdentifierMap));
            }
            return true;
        }

    //TODO: change to update logic and save body
    private SessionData saveRequest(String url, Map<Pattern, String> currentIdentifierMap, HttpServletRequest request){
        return sessionDataRepository.save(new SessionData(url, currentIdentifierMap));
    }

    @PostConstruct
    private void init(){}
}
