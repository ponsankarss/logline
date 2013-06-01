package com.vijayrc.supportguy.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyRecordSet {
    private Query query;
    private List<String> columns = new ArrayList<>();
    private List<MyTuple> tuples = new ArrayList<>();

    public MyRecordSet(Query query) {
        this.query = query;
    }

    public void addColumn(String column) {
        columns.add(column);
    }

    public void addTuple(MyTuple myTuple) {
        tuples.add(myTuple);
    }

    public String getName(){
        return query.getFullName();
    }

    @Override
    public String toString() {
        return "columns="+columns+"|tuples no="+tuples.size();
    }
}
