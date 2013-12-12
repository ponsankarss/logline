package com.vijayrc.supportguy.domain;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Data
public class Queue {
    private String name;
    private String type;
    private DateTime timeStamp;
    private int depth;
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        messages.add(message);
    }

    public boolean isEmpty() {
        return depth == 0;
    }
}
