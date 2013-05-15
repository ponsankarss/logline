package com.vijayrc.supportguy.util;

import java.util.List;

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
