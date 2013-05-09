package com.vrc.logline.config;

import com.vrc.logline.container.ConfigClass;

@ConfigClass(file = "dbs.json", order = 0)
public class SqlConfig {
    private String name;
    private String query;
    private String threshold;

    public SqlConfig() {
    }

    public SqlConfig(String query, String threshold, String name) {
        this.query = query;
        this.threshold = threshold;
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public String getThreshold() {
        return threshold;
    }
}
