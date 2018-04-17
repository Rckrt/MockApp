package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.InvalidScriptParameters;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import groovy.lang.GroovyShell;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();


    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService) {
        this.requestMappingService = requestMappingService;
    }

    @PostConstruct
    //TODO check state and save it or invalidate
    private void init(){}

    @Override
    //TODO: build request instead of save
    public void addToSession(RequestPattern requestPattern, HttpServletRequest request, String body) throws
            DefaultDataNotFound, PreviousRequestNotExist {
        log.info("Start added request to session");

        List<Pattern> identifierPatterns = requestPattern.getIdentifierPatterns();
        Map<Pattern,String> currentIdentifierMap = buildPatternValueMap(identifierPatterns, request);

        if (!isPreviousRequestExist(requestPattern, request, currentIdentifierMap, identifierPatterns))
            throw new PreviousRequestNotExist(request);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
    }

    @Override
    public Map<String,String> getResponseParamMap(RequestPattern requestPattern, HttpServletRequest request)
            throws InvalidScriptParameters, IOException {
        log.info("Start response data retrieving");
        List<String> scriptParams = requestPattern
                .getScriptParamPatterns(requestPattern.getScriptParams())
                .stream()
                .map(pattern -> pattern.getPatternValue(request))
                .collect(Collectors.toList());
        return executeScript(requestPattern, scriptParams);
    }

    private Map<Pattern,String> buildPatternValueMap(List<Pattern> patternList, HttpServletRequest request){
        return patternList
                .stream()
                .collect(Collectors.toMap(
                        pattern -> pattern, pattern -> pattern.getPatternValue(request)));
    }


    private List<RequestPattern> getPreviousPatterns(RequestPattern requestPattern, List<Pattern> patternsList ) {
        return requestMappingService
                .getInputRequestPatterns(requestPattern).stream()
                .filter(pattern -> pattern.isContainsIdentifier(patternsList))
                .collect(Collectors.toList());
    }

    private boolean isPreviousRequestExist(RequestPattern requestPattern, HttpServletRequest request,
                                          Map<Pattern,String> currentIdentifierMap, List<Pattern> patternsList)
            throws PreviousRequestNotExist {
        log.info("Check previous request existence for request {} and request pattern {}", request, requestPattern);
        if (!requestPattern.isInitial()) {
            List<RequestPattern> previousRequestPatterns = getPreviousPatterns(requestPattern, patternsList);
            try (Stream<RequestPattern> str = previousRequestPatterns.stream()){
                sessionAttributes.remove(str
                            .filter(pattern -> sessionAttributes.get(pattern).contains(currentIdentifierMap))
                            .findFirst().get());
                return true;
            }
            catch (Exception e){
                log.error("Catch error - previous request not exist", e);
                throw new PreviousRequestNotExist(request);
            }
        }
            return true;
        }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null) {
            sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
            log.info("request identifiers saved");
        }
    }


    private Map<String, String> executeScript(RequestPattern requestPattern, List<String> params) throws IOException {
        return (Map<String, String>) new GroovyShell()
                .parse( new File( requestPattern.getScript() ) )
                .invokeMethod("main", params);
    }
}
