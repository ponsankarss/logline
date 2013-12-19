package com.vijayrc.supportguy.domain;

import com.ibm.mq.MQMessage;
import com.ibm.mq.pcf.*;
import lombok.extern.log4j.Log4j;

@Log4j
public class ChannelStatus implements CMQCFC {
    private PCFAgent iAgent;
    private PCFParameter[] iParameters;

    public ChannelStatus(PCFAgent agent) {
        iAgent = agent;
        iParameters = new PCFParameter[]{
                new MQCFST(MQCACH_CHANNEL_NAME, "*"),
                new MQCFIL(MQIACH_CHANNEL_INSTANCE_ATTRS,
                new int[]{MQIACF_ALL})
        };
    }

    public Channel[] all() {
        try {
            MQMessage[] pcfResponses = iAgent.send(MQCMD_INQUIRE_CHANNEL_STATUS, iParameters);
            Channel[] channels = new Channel[pcfResponses.length];
            for (int i = 0; i < pcfResponses.length; i++)
                channels[i] = new Channel(pcfResponses[i]);
            return channels;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}


