package com.vijayrc.supportguy.queue.queue.domain;

import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
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
        MQEnvironment.hostname = "nasmsgdev02";
        MQEnvironment.port = 1420;
        MQEnvironment.channel = "NASC.WAS.SVRCONN";
        MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
        MQQueueManager queueManager = new MQQueueManager("MSGDEV02D7");

        return this;
    }
}
