package com.vijayrc.supportguy.domain;

import java.util.ArrayList;
import java.util.List;

public class MyRecordSet {

    private List<String> columns = new ArrayList<>();
    private List<MyTuple> tuples = new ArrayList<>();

    public void addColumn(String column) {
        columns.add(column);
    }

    public void addTuple(MyTuple myTuple) {
        tuples.add(myTuple);
    }

    public List<String> columns() {
        return columns;
    }
}
