package com.vrc.logline.config;

import com.vrc.logline.container.ConfigClass;

@ConfigClass(file="urls.json", order = 0)
public class UrlConfig {
    private String address;
    private String method;

    public String getAddress() {
        return address;
    }
}
