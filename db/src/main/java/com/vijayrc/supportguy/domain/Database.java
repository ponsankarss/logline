package com.vijayrc.supportguy.domain;

import lombok.Data;

@Data
public class Database {
    private String name;
    private String driver;
    private String url;
    private String user;
    private String password;
    private String schema;

    public String schemaQuery() {
        if (url.contains("postgres")) return "SET search_path = " + schema;
        if (url.contains("db2")) return "SET currentschema = " + schema;
        return null;
    }


}
