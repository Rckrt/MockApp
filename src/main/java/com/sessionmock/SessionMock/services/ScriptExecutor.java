package com.sessionmock.SessionMock.services;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.sessionmock.SessionMock.model.constants.Constants.SCRIPT_PATH;

public class ScriptExecutor {

    public static Map<String, Object> executeScript(String script, List<String> params) throws IOException {
        return (Map<String, Object>) new GroovyShell()
                .parse(new File(SCRIPT_PATH  + File.separator + script))
                .invokeMethod("main", params.toArray());
    }

    public static boolean executeValidateScript(String script, Map<String, List<String>> params) throws IOException {
        return (boolean) new GroovyShell()
                .parse(new File(SCRIPT_PATH  + File.separator + script))
                .invokeMethod("main", params);
    }

}
