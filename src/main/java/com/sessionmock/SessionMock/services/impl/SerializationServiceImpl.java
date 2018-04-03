package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.SerializationService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SerializationServiceImpl implements SerializationService {

  @Value("${application.static.resources.patterns}")
  private String requestPatternsPath;

  @Value("${application.static.resources.scenarios}")
  private String scenariosPath;

  private List<RequestPattern> requestPatterns;

  private List<List<RequestPattern>> scenariosList;

  @PostConstruct
  private void serializeAllRequestPatterns() {
    this.requestPatterns = Arrays.stream(getAllFiles(requestPatternsPath))
        .map(this::serializeRequestPattern)
        .collect(Collectors.toList());
    serializeAllScenarios();
  }

  private void serializeAllScenarios() {
    this.scenariosList = Arrays.stream(getAllFiles(requestPatternsPath))
        .map(this::getPatternListFromFile)
        .collect(Collectors.toList());
  }

  private File[] getAllFiles(String path){
    return (new File(requestPatternsPath)).listFiles();
  }


  @Override
  public List<List<RequestPattern>> getScenariosList() {
    return scenariosList;
  }

  @Override
  public RequestPattern findPatternByNickname(String nickname) {
    return null;
  }

  private List<RequestPattern> getPatternListFromFile(File file){
    try {
      return Files
          .lines(file.toPath())
          .map(this::findPatternByNickname)
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new NullPointerException("No scenarios found");
  }

  private RequestPattern serializeRequestPattern(File file){
    return null;
  }
}
