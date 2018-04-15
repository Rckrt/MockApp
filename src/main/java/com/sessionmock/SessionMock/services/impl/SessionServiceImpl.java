package com.sessionmock.SessionMock.services.impl;

import com.mongodb.BasicDBObject;
import com.sessionmock.SessionMock.exceptions.DefaultDataNotFound;
import com.sessionmock.SessionMock.exceptions.PreviousRequestNotExist;
import com.sessionmock.SessionMock.model.patterns.Pattern;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.repositories.SessionDataRepository;
import com.sessionmock.SessionMock.services.RequestMappingService;
import com.sessionmock.SessionMock.services.SerializationService;
import com.sessionmock.SessionMock.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SessionServiceImpl implements SessionService{

    private final RequestMappingService requestMappingService;
    private final SessionDataRepository sessionDataRepository;
    private final SerializationService serializationService;
    private Map<RequestPattern, List<Map<Pattern,String>>> sessionAttributes;


    @Autowired
    public SessionServiceImpl(RequestMappingService requestMappingService, SessionDataRepository sessionDataRepository, SerializationService serializationService) {
        this.requestMappingService = requestMappingService;
        this.sessionDataRepository = sessionDataRepository;
        this.serializationService = serializationService;
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
    public SessionData findResponse(RequestPattern requestPattern, HttpServletRequest request, String body) throws CloneNotSupportedException, DefaultDataNotFound, PreviousRequestNotExist {
        Map<Pattern,String> currentIdentifierMap = buildIdentifierMap(requestPattern.getIdentifierPatterns(), request);
        if (!isPreviousRequestExist(requestPattern, request, currentIdentifierMap)) throw new PreviousRequestNotExist(request);
        saveSessionAttributeIdentifier(requestPattern, currentIdentifierMap);
        return saveRequest(requestPattern.getUrlPattern(), currentIdentifierMap, body, isDataMustBeUpdated());
    }

    private boolean isDataMustBeUpdated() {
        return false;
    }

    private void saveSessionAttributeIdentifier(RequestPattern requestPattern, Map<Pattern, String> currentIdentifierMap) {
        if (currentIdentifierMap != null)
        sessionAttributes.computeIfAbsent(requestPattern, k -> new ArrayList<>()).add(currentIdentifierMap);
    }

    private boolean isPreviousRequestExist(RequestPattern requestPattern, HttpServletRequest request,
                                          Map<Pattern,String> currentIdentifierMap) throws PreviousRequestNotExist {
        if (!requestPattern.isInitial()) {
            List<RequestPattern> previousRequestPatterns = getPreviousPatterns(requestPattern, request);
            try (Stream<RequestPattern> str = previousRequestPatterns.stream()){
                return str.anyMatch(pattern -> sessionAttributes.get(pattern).contains(currentIdentifierMap));
            }
            catch (NullPointerException e){
                throw new PreviousRequestNotExist(request);
            }
        }
            return true;
        }

    //TODO: change to update logic and save body
    private SessionData saveRequest(String url, Map<Pattern, String> currentIdentifierMap, String body , boolean isUpdate)
            throws CloneNotSupportedException, DefaultDataNotFound {
        SessionData dataUnderUrl =  sessionDataRepository.findByUrlPatternAndSessionAttributeValues(url, currentIdentifierMap);
        if (dataUnderUrl == null) {
            dataUnderUrl = getDefaultSessionData(url).clone();
        }
        if (isUpdate) dataUnderUrl.setData(BasicDBObject.parse(body));
        dataUnderUrl.setSessionAttributeValues(currentIdentifierMap);
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
