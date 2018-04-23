package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SessionService {

    private final RequestMappingService requestMappingService;
    private final Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();


    @Autowired
    public SessionService(RequestMappingService requestMappingService) {
        this.requestMappingService = requestMappingService;
    }

    public void addToSession(RequestPattern requestPattern, HttpServletRequest request) throws PreviousRequestNotExist {
        log.info("Start added request to session");

        List<Pattern> identifierPatterns = requestPattern.getIdentifierPatterns();
        Map<Pattern,String> currentIdentifierMap = buildPatternValueMap(identifierPatterns, request);

        checkPreviousRequestExistence(requestPattern, request, currentIdentifierMap, identifierPatterns);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
    }

    private Map<Pattern,String> buildPatternValueMap(List<Pattern> patternList, HttpServletRequest request){
        return patternList
                .stream()
                .collect(Collectors.toMap(
                        pattern -> pattern, pattern -> pattern.getPatternValue(request)));
    }

    public void clearAllSessionAttributes() {
        sessionAttributes.clear();
    }

    private void checkPreviousRequestExistence(RequestPattern requestPattern, HttpServletRequest request,
                                               Map<Pattern,String> currentIdentifierMap, List<Pattern> patternsList)
            throws PreviousRequestNotExist {
        log.info("Check previous request existence for request {} and request pattern {}", request, requestPattern);
        if (!requestPattern.isInitial()) {
            try{
               sessionAttributes.remove(requestMappingService
                            .getInputRequestPatterns(requestPattern).stream()
                            .filter(pattern -> pattern.isContainsIdentifier(patternsList)
                                    && isSessionContainsIdentifier(currentIdentifierMap, pattern))
                            .findAny().get());
            }
            catch (Exception e){
                log.error("Catch error - previous request not exist", e);
                throw new PreviousRequestNotExist(request);
            }
        }
    }

    private boolean isSessionContainsIdentifier(Map<Pattern,String> map ,RequestPattern requestPattern ){
        return sessionAttributes.getOrDefault(requestPattern, Collections.EMPTY_LIST).contains(map);
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null) {
            sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
            log.info("request identifiers saved");
        }
    }
}
