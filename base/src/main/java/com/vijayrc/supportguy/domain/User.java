package com.vijayrc.supportguy.domain;

public class User {
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean nameIs(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
