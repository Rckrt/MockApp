package com.sessionmock.SessionMock.exceptions;

import com.sessionmock.SessionMock.model.patterns.Pattern;

import java.util.List;

public class InvalidScriptParameters extends NullPointerException {

    public InvalidScriptParameters(List<String> required, List<Pattern> found) {
        super("Some script parameters not found. Required: " + required.toString() + " Found: " + found.toString());
    }
}
