package com.sessionmock.SessionMock.model;

import com.sessionmock.SessionMock.exceptions.UrlNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sessionmock.SessionMock.model.constants.Constants.PATH_DELIMITER;
import static com.sessionmock.SessionMock.model.constants.Constants.URL_REGEX_PATTERN;

@Slf4j
//todo maybe move to service
public class UrlResolver {
    private static UrlResolver ROOT = new UrlResolver();
    private final String path;
    @Getter
    private String fullPath;
    @Getter
    private List<UrlResolver> childes;

    private UrlResolver(){
        this.childes = new ArrayList<>();
        this.fullPath = "";
        this.path = "";
    }

    private UrlResolver(String key, String fullPath) {
        this.path = key;
        this.fullPath = fullPath;
        this.childes = new ArrayList<>();
    }

    public static void setPrefix(String prefix){
        validateRelativeUrl(prefix);
        UrlResolver newResolver = new UrlResolver();
        List<UrlResolver> oldRootChildes = ROOT.childes;
        ROOT = newResolver;
        for(String key : prefix.split(PATH_DELIMITER)) {
            if ("".equals(key)) continue;
            String fullPath = PATH_DELIMITER + newResolver.fullPath + key;
            newResolver = newResolver.addChild(key);
            newResolver.fullPath = fullPath;
        }
        newResolver.childes = oldRootChildes;
    }


    //TODO throw custom exception
    private static void validateRelativeUrl(String url){
        if (!url.matches(URL_REGEX_PATTERN))
            throw new IllegalArgumentException("Prefix must be valid URL path");

    }

    public static void addUrl(String url){
        UrlResolver current = ROOT;
        for(String key : url.split(PATH_DELIMITER)) {
            if ("".equals(key)) continue;
            current = current.addChild(key);
        }
    }

    public static List<String> findUrl(String url) throws UrlNotFoundException {
        log.info("try to search url in tree {}", url);
        List<UrlResolver> current = new ArrayList<>();
        current.add(ROOT);

        for(String key : url.split(PATH_DELIMITER)) {
            if ("".equals(key)) continue;
            current = findChildByKey(key, current);
        }
        return current.stream()
                .map(UrlResolver::getFullPath)
                .collect(Collectors.toList());
    }

    private UrlResolver addChild(String key){
        UrlResolver child = findChildByKey(key);
       if (child == null){
           String fullPath = this.fullPath + PATH_DELIMITER + key;
           child = new UrlResolver(key, fullPath);
           childes.add(child);
       }
        return child;
    }

    private static List<UrlResolver> findChildByKey(String key, List<UrlResolver> current) throws UrlNotFoundException {
        return current.stream().flatMap(resolver -> resolver.getChildes().stream())
                .filter(ell -> key.equals(ell.path) || key.matches(ell.path))
                .collect(Collectors.toList());
    }

    private UrlResolver findChildByKey(String key) {
        return childes.stream()
                .filter(ell -> key.equals(ell.path))
                .findFirst().orElse(null);
    }
}
