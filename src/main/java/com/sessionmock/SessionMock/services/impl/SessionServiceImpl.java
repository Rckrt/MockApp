package com.sessionmock.SessionMock.services.impl;

import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.enums.RequestType;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.repositories.SessionDataRepository;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SerializationService;
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
    private final SessionDataRepository sessionDataRepository;
    private final SerializationService serializationService;
    private final Set<RequestType> updatable = new HashSet<>(Arrays.asList(RequestType.POST, RequestType.PUT));
    private Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes = new HashMap<>();


    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService, SessionDataRepository sessionDataRepository, SerializationService serializationService) {
        this.requestMappingService = requestMappingService;
        this.sessionDataRepository = sessionDataRepository;
        this.serializationService = serializationService;
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

    private List<String> getIdentifierValues(List<Pattern> patternList, HttpServletRequest request){
        List<String> identifierPatternsValues = new ArrayList<>();
        for (Pattern pattern : patternList) {
            identifierPatternsValues.add(pattern.getPatternValue(request));
        }
        return identifierPatternsValues;
    }



    @Override
    public SessionData findResponse(RequestPattern requestPattern, HttpServletRequest request, String body) throws
            DefaultDataNotFound, PreviousRequestNotExist {
        log.info("Start request saving and data retrieving for request {} with body {}", request, body);

        List<Pattern> identifierPatterns = requestPattern.getIdentifierPatterns();
        identifierPatterns.sort(Comparator.comparingInt(Pattern::hashCode));
        List<String> identifierPatternsValues = getIdentifierValues(identifierPatterns, request);
        Map<Pattern,String> currentIdentifierMap = buildIdentifierMap(identifierPatterns, request);

        if (!isPreviousRequestExist(requestPattern, request, currentIdentifierMap)) throw new PreviousRequestNotExist(request);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
        return saveRequest(requestPattern.getUrlPattern(), identifierPatterns, identifierPatternsValues,
                body, isDataMustBeUpdated(requestPattern));
    }

    private boolean isDataMustBeUpdated(RequestPattern requestPattern) {
        return requestPattern.isUpdatable() && updatable.contains(requestPattern.getRequestMethod());
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
                return str.anyMatch(pattern -> sessionAttributes.get(pattern).contains(currentIdentifierMap));
            }
            catch (NullPointerException e){
                log.error("Catch error - previous request not exist", e);
                throw new PreviousRequestNotExist(request);
            }
        }
            return true;
        }


    //TODO: change to update, delete and get logic
    private SessionData saveRequest(String url, List<Pattern> patterns, List<String> values, String body , boolean isUpdate)
            throws DefaultDataNotFound {
        log.info("Start saving request for url {} with body {}",url, body);
        SessionData dataUnderUrl =  sessionDataRepository.findByUrlPatternAndPatternsAndPatternValues(url, patterns, values );
        if (dataUnderUrl == null) {
            log.info("Save based on default data");
            dataUnderUrl = new SessionData(getDefaultSessionData(url));
        }
        if (isUpdate) dataUnderUrl.setData(BasicDBObject.parse(body));
        dataUnderUrl.addSessionAttributeValues(patterns, values);
        return sessionDataRepository.save(dataUnderUrl);
    }

    private SessionData getDefaultSessionData(String url) throws DefaultDataNotFound {
        return serializationService.getDefaultSessionData().stream()
                .filter(sessionData -> sessionData.getUrlPattern().equals(url))
                .findFirst()
                .orElseThrow(() -> new DefaultDataNotFound(url));
    }

    @PostConstruct
    //TODO check state and save it or invalidate
    private void init(){
        addDefaultData();
    }

    private void addDefaultData() {
       sessionDataRepository.saveAll(serializationService.getDefaultSessionData());
    }
}
