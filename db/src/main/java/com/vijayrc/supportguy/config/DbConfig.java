package com.vijayrc.supportguy.config;

import com.vijayrc.supportguy.meta.ConfigClass;

import java.util.List;

@ConfigClass(file="dbs.json", order = 1)
public class DbConfig {
    private String ip;
    private String port;
    private String user;
    private String password;
    private String database;
    private String schema;

    private List<String> sqls;

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getSchema() {
        return schema;
    }

    public List<String> getSqls() {
        return sqls;
    }
}
