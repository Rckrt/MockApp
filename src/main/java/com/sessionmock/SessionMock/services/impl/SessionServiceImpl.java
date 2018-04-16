package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();


    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService) {
        this.requestMappingService = requestMappingService;
    }

    //TODO: MOVE
    private List<RequestPattern> getPreviousPatterns(RequestPattern requestPattern) {
            List<Pattern> patternsList = requestPattern.getIdentifierPatterns();
            return requestMappingService
                    .getInputRequestPatterns(requestPattern).stream()
                    .filter(pattern -> pattern.isContainsIdentifier(patternsList))
                    .collect(Collectors.toList());
    }

    private Map<Pattern,String> buildIdentifierMap(List<Pattern> patternList, HttpServletRequest request){
        return patternList
                .stream()
                .collect(Collectors.toMap(
                        pattern -> pattern, pattern -> pattern.getPatternValue(request)));
    }

    @Override
    //TODO: build request instead of save
    public void addToSession(RequestPattern requestPattern, HttpServletRequest request, String body) throws
            DefaultDataNotFound, PreviousRequestNotExist {
        log.info("Start request saving and data retrieving for request {} with body {}", request, body);

        List<Pattern> identifierPatterns = requestPattern.getIdentifierPatterns();
        identifierPatterns.sort(Comparator.comparingInt(Pattern::hashCode));
        Map<Pattern,String> currentIdentifierMap = buildIdentifierMap(identifierPatterns, request);

        if (!isPreviousRequestExist(requestPattern, request, currentIdentifierMap)) throw new PreviousRequestNotExist(request);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null) {
            sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
            log.info("request identifiers saved");
        }
    }

    private boolean isPreviousRequestExist(RequestPattern requestPattern, HttpServletRequest request,
                                          Map<Pattern,String> currentIdentifierMap) throws PreviousRequestNotExist {
        log.info("Check previous request existence for request {} and request pattern {}", request, requestPattern);
        if (!requestPattern.isInitial()) {
            List<RequestPattern> previousRequestPatterns = getPreviousPatterns(requestPattern);
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

    @PostConstruct
    //TODO check state and save it or invalidate
    private void init(){}
}
