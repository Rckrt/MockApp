package com.sessionmock.SessionMock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionMockApplicationTests {

	@Test
	public void contextLoads() {
		new MockHttpServletRequest().;
		new MockHttpServletRequestBuilder().
	}

}
