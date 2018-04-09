package com.sessionmock.SessionMock.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sessionmock.SessionMock.model.SessionData;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import com.sessionmock.SessionMock.services.SerializationService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SerializationServiceImpl implements SerializationService {

  @Value("${application.static.resources.patterns}")
  private String requestPatternsPath;

  @Value("${application.static.resources.scenarios}")
  private String scenariosPath;

  @Value("${application.static.resources.default_data}")
  private String defaultDataPath;

  private List<RequestPattern> requestPatterns;

  private List<List<RequestPattern>> scenariosList;

  private List<SessionData> defaultData;

  private final ObjectMapper objectMapper;

  public SerializationServiceImpl() {
    objectMapper = new ObjectMapper();
  }

  @PostConstruct
  private void init() {
    serializeAllRequestPatterns();
    serializeAllScenarios();
    serializeAllDefaultData();
  }

  private void serializeAllDefaultData() {
    this.defaultData = Arrays.stream(getAllFiles(defaultDataPath))
            .map(e -> serializeClass(e, SessionData.class))
            .collect(Collectors.toList());
  }

  private void serializeAllScenarios() {
    this.scenariosList = Arrays.stream(getAllFiles(scenariosPath))
        .map(this::getPatternListFromFile)
        .collect(Collectors.toList());
  }

  private void serializeAllRequestPatterns(){
    this.requestPatterns = Arrays.stream(getAllFiles(requestPatternsPath))
            .map(e -> serializeClass(e, RequestPattern.class))
            .collect(Collectors.toList());
  }

  private File[] getAllFiles(String path){
    return (new File(path)).listFiles();
  }


  @Override
  public List<List<RequestPattern>> getScenariosList() {
    return scenariosList;
  }

  @Override
  public RequestPattern findPatternByNickname(String nickname) {
    return requestPatterns.stream()
            .filter(a -> a.getNickname().equals(nickname))
            .findFirst().get();
  }

  @Override
  public List<SessionData> getDefaultSessionData() {
    return defaultData;
  }

  private List<RequestPattern> getPatternListFromFile(File file){
    try {
      return Files
          .lines(file.toPath())
          .map(this::findPatternByNickname)
          .collect(Collectors.toList());
    } catch (IOException ignored) {
      throw new NullPointerException("No scenarios found");
    }
  }

  private <T> T serializeClass(File file, Class<T> cls){
    try {
      return objectMapper.readValue(file, cls);
    } catch (IOException ignored) {
      log.info("serialization error {}" ,ignored);
    throw new NullPointerException("can't serialize " + file.getPath());
    }
  }
}
