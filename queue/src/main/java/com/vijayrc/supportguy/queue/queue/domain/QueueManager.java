package com.vijayrc.supportguy.queue.queue.domain;

import lombok.Data;

import java.util.List;

@Data
public class QueueManager {
    private String name;
    private String host;
    private String port;
    private List<Queue> queues;
}
