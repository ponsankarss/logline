package com.vijayrc.supportguy.domain;

import lombok.Data;
import org.apache.commons.httpclient.NameValuePair;

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
    private Map<String, String> paramsMap = new HashMap<>();

    public boolean hasParams() {
        return params != null && !params.isEmpty();
    }

    public boolean isGet() {
        return method.equalsIgnoreCase("get");
    }

    public boolean isPost() {
        return method.equalsIgnoreCase("post");
    }

    public void addParam(String key, String value) {
        paramsMap.put(key, value);
    }

    public String getFullName() {
        return environment + "-" + name;
    }

    public  NameValuePair[] nameValuePairs() {
        if (!hasParams()) return new NameValuePair[0];
        NameValuePair[] nameValuePairs = new NameValuePair[getParams().size()];
        Map<String, String> paramsMap = getParamsMap();
        int i = 0;
        for (String paramKey : paramsMap.keySet()) {
            nameValuePairs[i] = new NameValuePair(paramKey, paramsMap.get(paramKey));
            ++i;
        }
        return nameValuePairs;
    }

    public boolean isNotPost() {
        return isGet() || isSSL();
    }

    public boolean isNotGet() {
        return isPost() || isSSL();
    }

    public boolean isSSL() {
        return url.contains("https");
    }
}
