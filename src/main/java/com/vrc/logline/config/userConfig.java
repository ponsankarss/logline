package com.vrc.logline.config;

import com.vrc.logline.container.ConfigClass;

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
