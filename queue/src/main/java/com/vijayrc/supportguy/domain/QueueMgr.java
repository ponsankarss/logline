package com.vijayrc.supportguy.domain;

import com.ibm.mq.*;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static com.ibm.mq.MQC.*;

@Data
@Log4j
public class QueueMgr {
    private String name;
    private String host;
    private String channel;
    private int port;
    private List<Queue> queues;

    public QueueMgr connect() throws Exception{
        initialize();
        MQQueueManager queueMgr = new MQQueueManager(name);

        for (Queue queue : queues) {
            MQQueue mqQueue = queueMgr.accessQueue(queue.getName(), MQOO_OUTPUT | MQOO_SET | MQOO_INQUIRE | MQOO_BROWSE| MQOO_FAIL_IF_QUIESCING);
            int depth = mqQueue.getCurrentDepth();
            queue.setDepth(depth);
            QueueMgr.log.info(queue.getName() + "=>" + depth);
            if(queue.isEmpty()) continue;

            MQGetMessageOptions options = new MQGetMessageOptions();
            options.options = MQGMO_WAIT | MQGMO_BROWSE_FIRST;
            MQMessage message = new MQMessage();
            boolean done = false;
            int count= 0;
            do {
                try {
                    message.clearMessage();
                    message.correlationId = MQCI_NONE;
                    message.messageId = MQMI_NONE;

                    mqQueue.get(message, options);
                    String msg = message.readLine();
                    queue.addMessage(msg);
                    QueueMgr.log.info("browsed message:["+msg+"]");

                    options.options = MQGMO_WAIT | MQGMO_BROWSE_NEXT;
                    count++;
                    if(count > 10) done = true;
                } catch (MQException ex) {
                    QueueMgr.log.error("mq exception:[CC=" + ex.completionCode + "|RC=" + ex.reasonCode+"]");
                    done = true;
                } catch (java.io.IOException ex) {
                    QueueMgr.log.error("exception:" + ex);
                    done = true;
                }finally {
                    queueMgr.close();
                }

            } while (!done);
        }
        return this;
    }

    private void initialize() {
        MQEnvironment.hostname = host;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;
        MQEnvironment.properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES);
    }

}
