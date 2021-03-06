package com.sessionmock.SessionMock.services;

import groovy.lang.GroovyShell;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.sessionmock.SessionMock.model.constants.Constants.SCRIPT_PATH;

@Service
public class ScriptService {

    public Map<String, Object> executeScript(String script, List<String> params) throws IOException {
        return (Map<String, Object>) new GroovyShell()
                .parse(new File(SCRIPT_PATH  + File.separator + script))
                .invokeMethod("main", params.toArray());
    }

    public boolean executeValidateScript(String script, Map<String, List<String>> params) throws IOException {
        return (boolean) new GroovyShell()
                .parse(new File(SCRIPT_PATH  + File.separator + script))
                .invokeMethod("main", params);
    }

}
