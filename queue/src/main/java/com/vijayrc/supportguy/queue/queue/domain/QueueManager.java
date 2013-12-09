package com.vijayrc.supportguy.queue.queue.domain;

import com.ibm.mq.*;
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

        int openOptions =  MQOO_OUTPUT | MQOO_SET | MQOO_INQUIRE | MQOO_BROWSE| MQOO_FAIL_IF_QUIESCING;

        MQQueueManager queueManager = new MQQueueManager(name);
        for (Queue queue : queues) {
            MQQueue mqQueue = queueManager.accessQueue(queue.getName(), openOptions);
            log.info(queue.getName() + "=>" + mqQueue.getCurrentDepth());
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            gmo.options = MQGMO_WAIT | MQGMO_BROWSE_FIRST;
            MQMessage myMessage = new MQMessage();

            boolean done = false;
            int count= 0;
            do {
                try {
                    myMessage.clearMessage();
                    myMessage.correlationId = MQCI_NONE;
                    myMessage.messageId = MQMI_NONE;

                    mqQueue.get(myMessage, gmo);
                    String msg = myMessage.readString(myMessage.getMessageLength());
                    System.out.println("Browsed message: " + msg);

                    gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
                    count++;
                    if(count > 10) done = true;
                } catch (MQException ex) {
                    System.out.println("MQ exception: CC = " + ex.completionCode + " RC = " + ex.reasonCode);
                    done = true;
                } catch (java.io.IOException ex) {
                    System.out.println("Java exception: " + ex);
                    done = true;
                }

            } while (!done);

        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        return this;
    }

}
