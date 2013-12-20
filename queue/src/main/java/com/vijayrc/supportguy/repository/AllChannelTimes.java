package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.ChannelTime;

import java.util.ArrayList;
import java.util.List;

import static com.ibm.mq.constants.CMQCFC.*;

public class AllChannelTimes {
    private List<ChannelTime> list = new ArrayList<ChannelTime>();

    public static final String[] names = {
            "MQCACF_LAST_PUT_DATE", "MQCACF_LAST_PUT_TIME", "MQCACF_LAST_GET_DATE", "MQCACF_LAST_GET_TIME",
            "MQCACF_PUT_DATE", "MQCACF_PUT_TIME", "MQCACF_SERVICE_START_DATE", "MQCACF_SERVICE_START_TIME",
            "MQCACF_LAST_PUB_DATE", "MQCACF_LAST_PUB_TIME", "MQCACF_LAST_MSG_TIME", "MQCACF_LAST_MSG_DATE",
            "MQCACH_LAST_MSG_TIME", "MQCACH_LAST_MSG_DATE", "MQCACH_CHANNEL_START_TIME", "MQCACH_CHANNEL_START_DATE",
            "MQCACF_PUBLISH_TIMESTAMP"};

    public static final int[] keys = {
            MQCACF_LAST_PUT_DATE, MQCACF_LAST_PUT_TIME, MQCACF_LAST_GET_DATE, MQCACF_LAST_GET_TIME,
            MQCACF_PUT_DATE, MQCACF_PUT_TIME, MQCACF_SERVICE_START_DATE, MQCACF_SERVICE_START_TIME,
            MQCACF_LAST_PUB_DATE, MQCACF_LAST_PUB_TIME, MQCACF_LAST_MSG_TIME, MQCACF_LAST_MSG_DATE,
            MQCACH_LAST_MSG_TIME, MQCACH_LAST_MSG_DATE, MQCACH_CHANNEL_START_TIME, MQCACH_CHANNEL_START_DATE,
            MQCACF_PUBLISH_TIMESTAMP};

    public AllChannelTimes() {
        for (int i = 0; i < keys.length; i++)
            list.add(new ChannelTime(keys[i], names[i]));
    }

    public List<ChannelTime> all() {
        return list;
    }
}
