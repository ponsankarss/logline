package com.vijayrc.supportguy.domain;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class ChannelTime implements Comparable<ChannelTime> {
    private int key;
    private String name;
    private String value;

    public ChannelTime(int key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int compareTo(ChannelTime that) {
        return name.compareTo(that.name);
    }

    public String value() {
        return StringUtils.isNotBlank(value) ? value : "";
    }
}
