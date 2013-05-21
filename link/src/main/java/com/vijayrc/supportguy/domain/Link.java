package com.vijayrc.supportguy.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Link {
    private String name;
    private String url;
    private String method;
    private String environment;
    private List<String> params;
    private Map<String,String> paramsMap =  new HashMap<>();

    public boolean hasParams() {        
        return params == null || params.isEmpty();
    }

    public boolean isGet(){
        return method.equalsIgnoreCase("get");
    }

    public boolean isPost(){
        return method.equalsIgnoreCase("post");
    }
    
    public void addParam(String key, String value){
        paramsMap.put(key, value);
    }

}
