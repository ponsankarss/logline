package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.ChannelTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ibm.mq.constants.CMQCFC.*;

public class AllChannelTimes {
    private List<ChannelTime> list = new ArrayList<>();

    public AllChannelTimes() {
        int[] keys = {MQCACH_LAST_MSG_TIME, MQCACH_LAST_MSG_DATE, MQCACH_CHANNEL_START_TIME, MQCACH_CHANNEL_START_DATE};
        String[] names = {"last-msg-time", "last-msg-date", "channel-start-time", "channel-start-date"};
        for (int i = 0; i < keys.length; i++)
            list.add(new ChannelTime(keys[i], names[i]));
    }

    public List<ChannelTime> all() {
        Collections.sort(list);
        return list;
    }
}
