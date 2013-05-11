package com.vijayrc.supportguy.config;

import com.vijayrc.supportguy.meta.ConfigClass;

@ConfigClass(file="servers.json",order = 1)
public class ServerConfig {
    private String name;
    private String ip;
    private String logDir;
    private String configDir;
    private String user;

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

}
