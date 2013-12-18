package com.vijayrc.supportguy.domain;

import com.ibm.mq.*;
import com.ibm.mq.pcf.PCFAgent;
import com.vijayrc.supportguy.channel.AllChannelStatus;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringEscapeUtils;

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
    private String description;

    public void connect() throws Exception{
        initialize();
        MQQueueManager queueMgr = new MQQueueManager(name);
        log.info("connecting to:"+queueMgr.getName());
        try {
            for (Queue queue : queues) {
                try {
                    MQQueue mqQueue = queueMgr.accessQueue(queue.getName(), MQOO_OUTPUT | MQOO_SET | MQOO_INQUIRE | MQOO_BROWSE| MQOO_FAIL_IF_QUIESCING);
                    int depth = mqQueue.getCurrentDepth();
                    queue.setDepth(depth);
                    log.info(queue.getName() + "=>" + depth);
                } catch (MQException ex) {
                    log.error("mq exception:[queue="+queue.getName()+"|CC=" + ex.completionCode + "|RC=" + ex.reasonCode + "]");
                }
            }
        } finally {
            queueMgr.close();
        }
    }

    public Queue browse(String queueName) throws Exception{
        Queue queue = pickQueue(queueName);
        queue.clearMsgs();
        initialize();

        MQQueueManager queueMgr = new MQQueueManager(name);
        MQQueue mqQueue = queueMgr.accessQueue(queueName, MQOO_OUTPUT | MQOO_SET | MQOO_INQUIRE | MQOO_BROWSE | MQOO_FAIL_IF_QUIESCING);
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

                String msg = message.readString(message.getMessageLength());
                queue.addMessage(StringEscapeUtils.escapeHtml(msg));
                options.options = MQGMO_WAIT | MQGMO_BROWSE_NEXT;
                log.info("browsed message:["+msg+"]");

                count++;
                if(count == 5) done = true;
            } catch (MQException ex) {
                log.error("mq exception:[CC=" + ex.completionCode + "|RC=" + ex.reasonCode+"]");
                done = true;
            } catch (java.io.IOException ex) {
                log.error("exception:" + ex);
                done = true;
            }finally {
                queueMgr.close();
            }

        } while (!done);

        return queue;
    }

    public void channelStatus() throws Exception {
        PCFAgent iAgent = new PCFAgent(host, port, channel);
        Channel[] channels = new AllChannelStatus(iAgent).getChannelStatus();
        log.info("fetched "+channels.length+" channels");
        iAgent.disconnect();
    }

    private void initialize() {
        MQEnvironment.hostname = host;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;
        MQEnvironment.properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES);
    }

    private Queue pickQueue(String queueName){
        for (Queue queue : queues)
            if(queue.nameIs(queueName)) return queue;
        return null;
    }
}
