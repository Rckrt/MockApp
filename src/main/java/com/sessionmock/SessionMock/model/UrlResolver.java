package com.sessionmock.SessionMock.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UrlResolver {
    @Getter
    private String path;
    @Getter
    private String fullPath;
    @Getter
    private List<UrlResolver> childes;

    public UrlResolver(String key) {
        this.path = key;
        this.childes = new ArrayList<>();
    }

    public UrlResolver addUrl(String url){
        UrlResolver current = this;
        for(String key : url.split("/")) {
            String fullPath = "/" + current.getFullPath() + key;
            current = current.addChild(key);
            current.fullPath = fullPath;
        }
        return current;
    }

    public boolean isUrlExist(String url){
        return false;
    }

    public RequestPattern findPatternByUrl(){
        return null;
    }


    private UrlResolver addChild(String key){
        UrlResolver child = findChildByKey(key);
       if (child == null){
           child = new UrlResolver(key);
           childes.add(child);
       }
        return child;
    }

    private UrlResolver findChildByKey(String key) {
        return childes.stream()
                .filter(ell -> ell.getPath().equals(key))
                .findFirst().orElse(null);
    }

}
