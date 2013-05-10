package com.vijayrc.supportguy.config;

import com.vijayrc.supportguy.meta.ConfigClass;

@ConfigClass(file="urls.json", order = 0)
public class UrlConfig {
    private String address;
    private String method;

    public String getAddress() {
        return address;
    }
}
