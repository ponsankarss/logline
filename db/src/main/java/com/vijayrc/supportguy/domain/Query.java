package com.vijayrc.supportguy.domain;

import lombok.Data;

@Data
public class Query {
    private String name;
    private String sql;
    private String db;
    private Database database;
}
