package com.vijayrc.supportguy.domain;

public class User {
    private String name;
    private String loginName;
    private String loginPassword;

    public String getName() {
        return name;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public boolean nameIs(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
