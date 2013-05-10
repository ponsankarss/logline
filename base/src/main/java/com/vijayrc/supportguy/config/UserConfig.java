package com.vijayrc.supportguy.config;

import com.vijayrc.supportguy.meta.ConfigClass;

@ConfigClass(file = "users.json", order = 0)
public class UserConfig {
    private String label;
    private String name;
    private String password;

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
