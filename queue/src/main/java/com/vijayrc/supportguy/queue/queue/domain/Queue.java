package com.vijayrc.supportguy.queue.queue.domain;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Data
public class Queue {
    private String name;
    private String depth;
    private String type;
    private DateTime timeStamp;
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        messages.add(message);
    }


}
