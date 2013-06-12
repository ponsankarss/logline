package com.vijayrc.supportguy.repository;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
@Scope("singleton")
@Log4j
public class AllDbErrors {
    private Map<String, Exception> map = new HashMap<>();

    public void add(String database, Exception e) {
        map.put(database, e);
    }

    public boolean has(String database) {
        return map.containsKey(database);
    }

    public void remove(String database) {
        if (map.containsKey(database)) map.remove(database);
    }

    public Exception getFor(String database) {
        return map.get(database);
    }
}


