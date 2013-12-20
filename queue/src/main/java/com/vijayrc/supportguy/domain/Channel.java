package com.vijayrc.supportguy.domain;

import com.ibm.mq.MQMessage;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.PCFParameter;
import com.vijayrc.supportguy.repository.AllChannelTimes;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;

import static com.ibm.mq.constants.CMQCFC.MQCACH_CHANNEL_NAME;
import static com.ibm.mq.constants.CMQCFC.MQIACH_CHANNEL_STATUS;

/**
 * Each hashtable represents a single mq channel.
 * The hashtable holds all data pertaining to the channel, and the data varies according to the channel.
 * For example, the sender channel holds details of target queue manager, while the receiver channel doesn't.
 */
@Log4j
public class Channel {
    private Map<Integer, Object> dataMap = new HashMap<>();

    public static final String[] statuses = {
            "Inactive", "Binding", "Starting", "Running", "Stopping", "Retrying",
            "Stopped", "Requesting", "Paused", "", "", "", "", "Initializing"};

    public Channel(MQMessage message) {
        super();
        try {
            MQCFH cfh = new MQCFH(message);
            int iReasonCode = cfh.reason;
            if (iReasonCode == 0) {
                PCFParameter p;
                for (int i = 0; i < cfh.parameterCount; i++) {
                    p = PCFParameter.nextParameter(message);
                    Integer key = p.getParameter();
                    Object value = p.getValue();
                    dataMap.put(key, value);
                    log.info("adding:" + key + "=>" + value);
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public String name() {
        return getStringValue(MQCACH_CHANNEL_NAME);
    }

    public String status() {
        try {
            return statuses[getIntValue(MQIACH_CHANNEL_STATUS)];
        } catch (NullPointerException channelIsInactive) {
            return statuses[0];
        }
    }

    public AllChannelTimes times() {
        AllChannelTimes times = new AllChannelTimes();
        for (ChannelTime channelTime : times.all()) {
            channelTime.setValue(getStringValue(channelTime.getKey()));
            log.info(channelTime);
        }
        return times;
    }

    public boolean isRunning(){
        return "Running".equals(status());
    }
    public boolean isStopped(){
        return "Stopped".equals(status());
    }
    public boolean isInactive(){
        return "Inactive".equals(status());
    }
    private int getIntValue(int key) {
        return (Integer) dataMap.get(key);
    }
    private String getStringValue(int key) {
        return ((String) dataMap.get(key));
    }
}
