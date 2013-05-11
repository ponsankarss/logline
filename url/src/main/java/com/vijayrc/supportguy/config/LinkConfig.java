package com.vijayrc.supportguy.config;

import com.vijayrc.supportguy.meta.ConfigClass;

@ConfigClass(file="links.json", order = 0)
public class LinkConfig {
    private String address;
    private String method;

    public String getAddress() {
        return address;
    }
}
