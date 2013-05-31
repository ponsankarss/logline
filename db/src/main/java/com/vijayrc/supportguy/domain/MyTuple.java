package com.vijayrc.supportguy.domain;

import java.util.ArrayList;
import java.util.List;

public class MyTuple {
    private List<String> items = new ArrayList<>();

    public void add(String item) {
        items.add(item);
    }
}
