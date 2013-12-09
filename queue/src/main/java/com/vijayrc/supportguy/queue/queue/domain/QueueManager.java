package com.vijayrc.supportguy.queue.queue.domain;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static com.ibm.mq.MQC.*;

@Data
@Log4j
public class QueueManager {
    private String name;
    private String host;
    private String channel;
    private int port;
    private List<Queue> queues;

    public QueueManager connect() throws Exception{
        MQEnvironment.hostname = host;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;
        MQEnvironment.properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES);

        MQQueueManager queueManager = new MQQueueManager(name);
        System.out.println(name);

        for (Queue queue : queues) {
            MQQueue mqQueue = queueManager.accessQueue(queue.getName(), MQOO_OUTPUT | MQOO_SET | MQOO_INQUIRE | MQOO_FAIL_IF_QUIESCING);
            log.info(queue.getName() + "=>" + mqQueue.getCurrentDepth());
        }
        return this;
    }

}
