package com.sessionmock.SessionMock.services;

import com.sessionmock.SessionMock.exceptions.PatternValidationException;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SessionService {

    private final RequestMappingService requestMappingService;
    private final Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();
    private final ScriptService scriptService;
    private final SerializationService serializationService;


    @Autowired
    public SessionService(RequestMappingService requestMappingService, ValidationService validationService, ScriptService scriptService, SerializationService serializationService) {
        this.requestMappingService = requestMappingService;
        this.scriptService = scriptService;
        this.serializationService = serializationService;
    }

    public void addToSession(RequestPattern requestPattern, HttpServletRequest request)
            throws PreviousRequestNotExist, IOException, PatternValidationException, RequestPatternNotFoundException {
        log.info("Start added request to session");

        Map<Pattern,String> allPatternValuesMap = buildPatternValueMap(requestPattern.getAllPatterns(), request);
        Map<Pattern,String> currentIdentifierMap = buildPatternValueMap(requestPattern.getIdentifierPatterns(), request);

        if (!requestPattern.isInitial()) {
            validateWithScript(request, requestPattern, allPatternValuesMap);
            checkPreviousRequestExistence(requestPattern, request, currentIdentifierMap);
        }
        saveSessionAttributeIdentifier(requestPattern, allPatternValuesMap);
    }


    private void validateWithScript(HttpServletRequest request, RequestPattern requestPattern, Map<Pattern,String> patternValueMap)
            throws IOException, PatternValidationException, RequestPatternNotFoundException {
        Map<String, List<String>> patternListMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry :requestPattern.getValidateLinks().entrySet()) {
            RequestPattern linkedPattern = serializationService.findPattern(entry.getKey());
            patternListMap.put(entry.getKey(), patternLinksToValues(entry.getValue(), linkedPattern, patternValueMap));
        }

        if (!scriptService.executeValidateScript(requestPattern.getValidateScript(), patternListMap))
            throw new PatternValidationException(requestPattern, request);
    }

    private List<String> patternLinksToValues(List<String> links, RequestPattern requestPattern, Map<Pattern,String> patternValueMap){
        List<String> patternValues = new ArrayList<>();
        Map<String, String> linkValueMap = findInSessionByIdentifier(patternValueMap, requestPattern)
            .entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey().buildScriptIdentifier(), Map.Entry::getValue));

        for (String link : links) patternValues.add(linkValueMap.get(link));
        return patternValues;
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
                                               Map<Pattern,String> currentIdentifierMap)
            throws PreviousRequestNotExist {
        log.info("Check previous request existence for request {} and request pattern {}", request, requestPattern);
        try{
           requestMappingService
                .getInputRequestPatterns(requestPattern).stream()
                .filter(pattern -> pattern.isContainsIdentifier(currentIdentifierMap.keySet())
                        && isSessionContainsIdentifier(currentIdentifierMap, pattern))
                .findAny().get();
        }
        catch (Exception e){
            log.error("Catch error - previous request not exist", e);
            throw new PreviousRequestNotExist(request);
        }
    }

    //custom exception
    private boolean isSessionContainsIdentifier(Map<Pattern,String> map ,RequestPattern requestPattern ){
        return sessionAttributes.get(requestPattern).
                stream().allMatch(entry -> entry.entrySet().containsAll(map.entrySet()));
    }

    private Map<Pattern, String> findInSessionByIdentifier(Map<Pattern,String> map ,RequestPattern requestPattern ){
        return sessionAttributes.get(requestPattern).stream()
                .filter(entry -> entry.entrySet()
                .containsAll(map.entrySet()))
                .findFirst().get();
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null) {
            sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
            log.info("request identifiers saved");
        }
    }
}
