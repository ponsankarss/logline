package com.vijayrc.supportguy.domain;

import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.PCFParameter;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Each hashtable represents a single mq channel.
 * The hashtable holds all data pertaining to the channel, and the data varies according to the channel.
 * For example, the sender channel holds details of target queue manager, while the receiver channel doesn't.
 */
@Log4j
public class Channel {
    private Map<Integer, Object> map = new HashMap<>();
    private int iReasonCode;
    public static final String[] statuses = {"Inactive", "Binding", "Starting", "Running", "Stopping", "Retrying", "Stopped", "Requesting", "Paused", "", "", "", "", "Initializing"};

    public Channel(MQMessage message) {
        super();
        try {
            MQCFH cfh = new MQCFH(message);
            iReasonCode = cfh.reason;
            if (isValid()) {
                PCFParameter p;
                for (int i = 0; i < cfh.parameterCount; i++) {
                    p = PCFParameter.nextParameter(message);
                    Integer key = p.getParameter();
                    Object value = p.getValue();
                    map.put(key, value);
//                    log.info("adding:" + key + "=>" + value);
                }
            }
        } catch (Exception ex) {
            Channel.log.error(ex);
        }
    }

    public boolean isValid() {
        return iReasonCode == 0;
    }

    public int getReasonCode() {
        return iReasonCode;
    }

    public int getIntValue(int key) {
        return (Integer) map.get(key);
    }

    public int[] getIntArray(int key) {
        return (int[]) map.get(key);
    }

    public String getStringValue(int key) {
        return ((String) map.get(key)).trim();
    }

    public String[] getStringArray(int key) {
        String[] paddedStrings = (String[]) map.get(key);
        String[] trimStrings = new String[paddedStrings.length];
        for (int i = 0; i < paddedStrings.length; i++)
            trimStrings[i] = paddedStrings[i].trim();
        return trimStrings;
    }

    public String status() {
        try {
            return statuses[getIntValue(CMQCFC.MQIACH_CHANNEL_STATUS)];
        } catch (NullPointerException channelIsInactive) {
            return statuses[0];
        }
    }

    public String name(){
        return getStringValue(CMQCFC.MQCACH_CHANNEL_NAME);
    }
}
