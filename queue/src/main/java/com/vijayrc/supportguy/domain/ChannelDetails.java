package com.vijayrc.supportguy.domain;

import com.ibm.mq.MQMessage;
import com.ibm.mq.pcf.*;
import lombok.extern.log4j.Log4j;

/**
 *
 * This class is used to query queue managers for details of all
 * the channels they own.
 * This query is currently limited to sender channels.
 */
@Log4j
public class ChannelDetails implements CMQCFC {
    private PCFAgent iAgent;
    private PCFParameter[] iParameters;

    /**
     * This constructor requires a valid queue manager agent. This
     * "channel details" request is currently only for sender channels
     * (channel type of MQCHT_SENDER), though this may easily be
     * changed to MQCHT_ALL.
     */
    public ChannelDetails(PCFAgent agent) {
        iAgent = agent;
        iParameters = new PCFParameter[]{
                new MQCFST(MQCACH_CHANNEL_NAME, "*"),
                new MQCFIN(MQIACH_CHANNEL_TYPE, CMQXC.MQCHT_ALL),
                new MQCFIL(MQIACF_CHANNEL_ATTRS, new int[]{MQIACF_ALL}),
        };
    }

    public Channel[] all() {
        try {
            MQMessage[] pcfResponses = iAgent.send(MQCMD_INQUIRE_CHANNEL, iParameters);
            Channel[] channels = new Channel[pcfResponses.length];
            for (int i = 0; i < pcfResponses.length; i++)
                channels[i] = new Channel(pcfResponses[i]);
            return channels;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

}