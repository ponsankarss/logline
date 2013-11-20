package com.vijayrc.supportguy.domain;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class Context {
    private int size;
    private String name;
    private String startTime;
    private String endTime;
    private List<Line> lines;

    public Context(String name, int size) {
        this.size = size;
        this.name = name;
        this.lines = new ArrayList<>();
    }

    public void add(Line line){
       line.withThread(name);
       lines.add(line);
    }

    public Context complete(){
        for (int i=lines.size()-1;i>=0;i--)
            if(lines.get(i).hasNoTime())lines.remove(i);
        lines= sort(lines, on(Line.class).timestamp());
        if(lines.isEmpty()) return this;

        startTime = lines.get(0).timestamp();
        endTime = lines.get(lines.size()-1).timestamp();
        return this;
    }

    public int size() {
        return size;
    }

    public String name(){
        return name;
    }

    public String startTime() {
        return StringUtils.isBlank(startTime)?"XX":startTime;
    }

    public String endTime() {
        return StringUtils.isBlank(endTime)?"XX":endTime;
    }

    public List<Line> lines() {
        return lines;
    }
}
