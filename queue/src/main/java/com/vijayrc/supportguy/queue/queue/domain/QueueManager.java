package com.vijayrc.supportguy.queue.queue.domain;

import com.ibm.mq.MQQueueManager;
import lombok.Data;

import java.util.List;

@Data
public class QueueManager {
    private String name;
    private String host;
    private String port;
    private String channel;
    private List<Queue> queues;

    public QueueManager connect() throws Exception{
        MQQueueManager queueManager = new MQQueueManager(name);

        return this;
    }
}
