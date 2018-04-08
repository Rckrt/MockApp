package com.sessionmock.SessionMock.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  private List<RequestPattern> requestPatterns;

  private List<List<RequestPattern>> scenariosList;

  private final ObjectMapper objectMapper;

  public SerializationServiceImpl() {
    objectMapper = new ObjectMapper();
  }

  @PostConstruct
  private void serializeAllRequestPatterns() {
    this.requestPatterns = Arrays.stream(getAllFiles(requestPatternsPath))
        .map(this::serializeRequestPattern)
        .collect(Collectors.toList());
    serializeAllScenarios();
  }

  private void serializeAllScenarios() {
    this.scenariosList = Arrays.stream(getAllFiles(scenariosPath))
        .map(this::getPatternListFromFile)
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

  private RequestPattern serializeRequestPattern(File file){
    try {
      return objectMapper.readValue(file, RequestPattern.class);
    } catch (IOException ignored) {
      log.info("serialization error {}" ,ignored);
    throw new NullPointerException("can't serialize " + file.getPath());
    }
  }
}
