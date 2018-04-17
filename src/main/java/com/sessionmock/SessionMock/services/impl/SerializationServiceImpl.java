package com.sessionmock.SessionMock.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sessionmock.SessionMock.exceptions.SerializationException;
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
  //TODO load groovy scripts
  private void init() {
    serializeAllRequestPatterns();
    serializeAllScenarios();
  }

  private void serializeAllScenarios() {
    this.scenariosList = Arrays.stream(getAllFiles(scenariosPath))
        .map(this::getPatternListFromFile)
        .collect(Collectors.toList());
    this.scenariosList.forEach(list -> list.get(0).setInitial(true));
  }

  private void serializeAllRequestPatterns(){
    this.requestPatterns = Arrays.stream(getAllFiles(requestPatternsPath))
            .map(e -> {
              try {
                return serializeClass(e, RequestPattern.class);
              } catch (SerializationException e1) {
                throw new RuntimeException(e1);
              }
            })
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

  private <T> T serializeClass(File file, Class<T> cls) throws SerializationException {
    try {
      return objectMapper.readValue(file, cls);
    } catch (IOException cause) {
      log.info("Serialization error {}", cause);
      throw new SerializationException(file, cls, cause);
    }
  }
}
