package com.vijayrc.supportguy.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyTuple {
    private List<String> items = new ArrayList<>();

    public void add(String item) {
        items.add(item);
    }
}
