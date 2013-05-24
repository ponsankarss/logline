package com.vijayrc.supportguy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkHit {
    private String name;
    private String response;
    private int statusCode;
    private boolean processed;

    public LinkHit(String name, int statusCode, String response) {
        this.name = name;
        this.statusCode = statusCode;
        this.response = response;
        this.processed = true;
    }

    private LinkHit(boolean processed) {
        this.processed = processed;
    }

    public static LinkHit noAction() {
        return new LinkHit(false);
    }
}
