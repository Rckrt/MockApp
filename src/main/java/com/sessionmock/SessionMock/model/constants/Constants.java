package com.sessionmock.SessionMock.model.constants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Constants {
    public static final String PATH_DELIMITER = "/";
    public static final String URL_REGEX_PATTERN = "(([a-zA-Z0-9/]+$)*)";
    public static final String JSON_EXTENSION = ".json";
    public static final String WORD_WITH_SPACES_PATTERN = "^([\\w+\\s])*+$";
    public static final String REQUEST_SET_DELIMETER = "\\s+";
    public static final String CURRENT_PATH = getJarDir();
    public static final String RESOURCE_PACKAGE = CURRENT_PATH + File.separator + "static resources";
    public static String TEMPLATE_PATH = RESOURCE_PACKAGE + File.separator + "templates";
    public static String SCRIPT_PATH = RESOURCE_PACKAGE + File.separator + "scripts";
    public static String PATTERNS_PATH = RESOURCE_PACKAGE + File.separator + "patterns";
    public static String SCENARIOS_PATH = RESOURCE_PACKAGE + File.separator + "scenarios";

    private static String getJarDir() {
        String path = Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = null;
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {}
        return decodedPath;
    }
}

