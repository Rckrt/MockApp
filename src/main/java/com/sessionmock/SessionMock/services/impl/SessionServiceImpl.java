package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private final Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();


    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService) {
        this.requestMappingService = requestMappingService;
    }

    @Override
    public void addToSession(RequestPattern requestPattern, HttpServletRequest request) throws PreviousRequestNotExist {
        log.info("Start added request to session");

        List<Pattern> identifierPatterns = requestPattern.getIdentifierPatterns();
        Map<Pattern,String> currentIdentifierMap = buildPatternValueMap(identifierPatterns, request);

        checkPreviousRequestExistance(requestPattern, request, currentIdentifierMap, identifierPatterns);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
    }

    private Map<Pattern,String> buildPatternValueMap(List<Pattern> patternList, HttpServletRequest request){
        return patternList
                .stream()
                .collect(Collectors.toMap(
                        pattern -> pattern, pattern -> pattern.getPatternValue(request)));
    }

    @Override
    public void clearAllSessionAttributes() {
        sessionAttributes.clear();
    }



    private List<RequestPattern> getPreviousPatterns(RequestPattern requestPattern, List<Pattern> patternsList ) {
        return requestMappingService
                .getInputRequestPatterns(requestPattern).stream()
                .filter(pattern -> pattern.isContainsIdentifier(patternsList))
                .collect(Collectors.toList());
    }

    private void checkPreviousRequestExistance(RequestPattern requestPattern, HttpServletRequest request,
                                          Map<Pattern,String> currentIdentifierMap, List<Pattern> patternsList)
            throws PreviousRequestNotExist {
        log.info("Check previous request existence for request {} and request pattern {}", request, requestPattern);
        if (!requestPattern.isInitial()) {
            List<RequestPattern> previousRequestPatterns = getPreviousPatterns(requestPattern, patternsList);
            try (Stream<RequestPattern> str = previousRequestPatterns.stream()){
                sessionAttributes.remove(str
                            .filter(pattern -> isSessionContainsIdentifier(currentIdentifierMap, pattern))
                            .findAny().get());
            }
            catch (Exception e){
                log.error("Catch error - previous request not exist", e);
                throw new PreviousRequestNotExist(request);
            }
        }
    }

    private boolean isSessionContainsIdentifier(Map<Pattern,String> map ,RequestPattern requestPattern ){
        return sessionAttributes.get(requestPattern).contains(map);
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null) {
            sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
            log.info("request identifiers saved");
        }
    }
}
