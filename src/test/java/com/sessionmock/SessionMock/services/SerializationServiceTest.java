package com.sessionmock.SessionMock.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sessionmock.SessionMock.exceptions.RequestPatternNotFoundException;
import com.sessionmock.SessionMock.model.patterns.RequestPattern;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.sessionmock.SessionMock.model.constants.Constants.JSON_EXTENSION;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "application.static.resources.patterns=src/test/resources/static resources/patterns",
        "application.static.resources.scenarios=src/test/resources/static resources/scenarios",
})
public class SerializationServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private  SerializationService serializationService;

    @TestConfiguration
    static class SerializationServiceTestContextConfiguration{

        @Bean
        public SerializationService serializationService(){
            return new  SerializationService();
        }
    }

    @Value("classpath:static resources/patterns")
    private Resource requestPatternsPath;

    @Test
    public void findPatternTest() throws IOException, RequestPatternNotFoundException {
        File randomRequestPattern = Arrays.stream(requestPatternsPath.getFile().listFiles()).findAny().get();
        RequestPattern requestPattern = objectMapper.readValue(randomRequestPattern, RequestPattern.class);
        String nickname = randomRequestPattern.getName().replace(JSON_EXTENSION, "");
        requestPattern.setNickname(randomRequestPattern.getName());
        Assert.assertEquals(serializationService.findPattern(nickname), requestPattern);
    }

    @Test
    public void scenarioOrderingTest() {}
}
