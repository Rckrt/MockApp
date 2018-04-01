package com.sessionmock.SessionMock.model;

import java.util.List;

public class RequestPatternVertex extends RequestPattern {

    private List<RequestPattern> beforePatterns;

    private List<RequestPattern> afterPatterns;
}
