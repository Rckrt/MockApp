package com.sessionmock.SessionMock.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.exceptions.SerializationException;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.sessionmock.SessionMock.model.constants.Constants.*;

@Service
@Slf4j
public class SerializationService {

  @Value("${application.static.resources.patterns}")
  private String requestPatternsPath;

  @Value("${application.static.resources.scenarios}")
  private String scenariosPath;

  @Value("${application.static.resources.templates}")
  private String templatePath;

  @Value("${application.static.resources.scripts}")
  private String scriptPath;

  private final List<RequestPattern> requestPatterns = new ArrayList<>();

  private final List<List<Set<RequestPattern>>> scenariosList = new ArrayList<>();;

  private final ObjectMapper objectMapper;

  public SerializationService() {
    objectMapper = new ObjectMapper();
  }

  @PostConstruct
  private void init() throws IOException, RequestPatternNotFoundException {
    serializeAllRequestPatterns();
    serializeAllScenarios();
    SCRIPT_PATH =  scriptPath;
    TEMPLATE_PATH = templatePath;
  }

  public List<List<Set<RequestPattern>>> getScenariosList() {
    return scenariosList;
  }

  private List<Set<RequestPattern>> getPatternSetListFromFile(File file)
          throws IOException, RequestPatternNotFoundException {
    List<Set<RequestPattern>> patternSetList = new ArrayList<>();
    for (String line : Files.readAllLines(file.toPath())) {
      patternSetList.add(parseScenario(line.trim(), new HashSet<>()));
    }
    patternSetList.get(0).forEach(pattern -> pattern.setInitial(true));
    return patternSetList;
  }

  public RequestPattern findPattern(String nickname) throws RequestPatternNotFoundException {
    return requestPatterns.stream()
            .filter(a -> a.getNickname().equals(nickname + JSON_EXTENSION))
            .findFirst().orElseThrow(() -> new RequestPatternNotFoundException(nickname));
  }

  private Set<RequestPattern> parseScenario(String scenariosStr, Set<RequestPattern> levelSet)
          throws RequestPatternNotFoundException {
    if (scenariosStr.matches(WORD_WITH_SPACES_PATTERN))
      fillPatternsSet(scenariosStr, levelSet);
    else levelSet.add(findPattern(scenariosStr));

    return levelSet;
  }

  private void fillPatternsSet(String scenariosStr, Set<RequestPattern> levelSet)
          throws RequestPatternNotFoundException {
    for (String pattern : scenariosStr.split(REQUEST_SET_DELIMETER))
      levelSet.add(findPattern(pattern.trim()));
  }

  private void serializeAllScenarios() throws IOException, RequestPatternNotFoundException {
    for (File file : getAllFiles(scenariosPath)) {
      List<Set<RequestPattern>> patternSetListFromFile = getPatternSetListFromFile(file);
      scenariosList.add(patternSetListFromFile);
    }
  }
  private void serializeAllRequestPatterns() throws SerializationException {
    for (File file : getAllFiles(requestPatternsPath)) {
      RequestPattern requestPattern = serializeClass(file, RequestPattern.class);
      requestPattern.setNickname(file.getName());
      requestPatterns.add(requestPattern);
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

  private File[] getAllFiles(String path) {
    return (new File(path)).listFiles();
  }
}