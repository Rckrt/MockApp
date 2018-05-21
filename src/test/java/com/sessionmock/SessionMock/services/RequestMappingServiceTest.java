package com.sessionmock.SessionMock.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "application.static.resources.patterns=src/test/resources/static resources/patterns",
        "application.static.resources.scenarios=src/test/resources/static resources/scenarios",
})
public class RequestMappingServiceTest {

    @Test
    public void findRequestPatternTest(){}

    @Test
    public void checkInputRequestPatternsTest(){}
}
