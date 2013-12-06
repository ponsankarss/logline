package com.vijayrc.supportguy.queue.queue.domain;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Queue {
    private String name;
    private String depth;
    private String type;
    private DateTime timeStamp;
}
