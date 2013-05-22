package com.vijayrc.supportguy.domain;

import lombok.Data;

@Data
public class LinkHit {
    private String name;
    private int statusCode;
    private String response;

    public LinkHit(String name, int statusCode, String response) {
        this.name = name;
        this.statusCode = statusCode;
        this.response = response;
    }

    public boolean isNotOk(){
        return statusCode != 200;
    }
}
