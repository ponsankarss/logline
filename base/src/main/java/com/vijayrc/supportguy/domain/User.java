package com.vijayrc.supportguy.domain;

import lombok.Data;

@Data
public class User {
    private String name;
    private String password;

    public boolean nameIs(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
