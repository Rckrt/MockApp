package com.sessionmock.SessionMock.services.impl;

import com.sessionmock.SessionMock.model.RequestPattern;
import com.sessionmock.SessionMock.services.SerializationService;
import java.io.File;
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

  private List<RequestPattern> requestPatterns;

  @PostConstruct
  public void serializeAllRequestPatterns() {
    this.requestPatterns = Arrays.stream(getAllFiles(requestPatternsPath))
        .map(this::serializeRequestPattern)
        .collect(Collectors.toList());
  }

  private File[] getAllFiles(String path){
    return (new File(requestPatternsPath)).listFiles();
  }


  @Override
  public List<List<RequestPattern>> getScenariosList() {
    return Collections.EMPTY_LIST;
  }

  private RequestPattern serializeRequestPattern(File file){
    return null;
  }
}
