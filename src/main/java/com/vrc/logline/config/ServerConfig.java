package com.vrc.logline.config;

import com.vrc.logline.container.ConfigClass;

@ConfigClass(file="servers.json",order = 1)
public class ServerConfig {
    private String name;
    private String ip;
    private String logDir;
    private String configDir;
    private UserConfig userConfig;

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getLogDir() {
        return logDir;
    }

    public String getConfigDir() {
        return configDir;
    }

    public UserConfig getUserConfig() {
        return userConfig;
    }
}
