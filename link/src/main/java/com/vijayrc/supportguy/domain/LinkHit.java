package com.vijayrc.supportguy.domain;

import lombok.Data;

@Data
public class LinkHit {
    private String link;
    private int statusCode;
    private String response;

    public LinkHit(String link, int statusCode, String response) {
        this.link = link;
        this.statusCode = statusCode;
        this.response = response;
    }

    public boolean isNotOk(){
        return statusCode != 200;
    }
}
