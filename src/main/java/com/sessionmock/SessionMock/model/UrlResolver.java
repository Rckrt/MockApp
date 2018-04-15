package com.sessionmock.SessionMock.model;

import com.sessionmock.SessionMock.exceptions.UrlNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.sessionmock.SessionMock.model.constants.Constants.PATH_PARAM_IDENTIFIER;

@Slf4j
public class UrlResolver {
    public static final UrlResolver ROOT = new UrlResolver();
    @Getter
    private String path;
    @Getter
    private String fullPath;
    @Getter
    private List<UrlResolver> childes;

    private UrlResolver(){
        this.childes = new ArrayList<>();
        this.fullPath = "";
        this.path = "";
    }

    private UrlResolver(String key) {
        this.path = key;
        this.childes = new ArrayList<>();
    }

    public void addUrl(String url){
        UrlResolver current = ROOT;
        for(String key : url.split("/")) {
            if ("".equals(key)) continue;
            String fullPath = "/" + current.getFullPath() + key;
            current = current.addChild(key);
            current.fullPath = fullPath;
        }
    }

    public String findUrl(String url) throws UrlNotFoundException {
        log.info("try ti search url in tree {}", url);
        UrlResolver current = ROOT;
        for(String key : url.split("/")) {
            if ("".equals(key)) continue;
            current = findChildByKey(key, current);
        }
        return current.getFullPath();
    }

    private UrlResolver addChild(String key){
        UrlResolver child = findChildByKey(key);
       if (child == null){
           child = new UrlResolver(key);
           childes.add(child);
       }
        return child;
    }

    private UrlResolver findChildByKey(String key, UrlResolver current) throws UrlNotFoundException {
        return current.getChildes().stream()
                .filter(ell -> ell.checkEquality(key))
                .findFirst().orElseThrow(() -> new UrlNotFoundException(current.fullPath + key));
    }

    private UrlResolver findChildByKey(String key) {
        return childes.stream()
                .filter(ell -> ell.checkEquality(key))
                .findFirst().orElse(null);
    }

    private boolean checkEquality(String key){
       return key.equals(path) || PATH_PARAM_IDENTIFIER.equals(path);
    }
}
