package com.vijayrc.supportguy.domain;

import org.apache.commons.lang.StringEscapeUtils;

public class Line implements Comparable<Line> {

    private String file;
    private String value;
    private String thread;
    private String timestamp;
    private String errorTitle;
    private boolean error;

    public Line(String value) {
        this.value = value;
    }

    public String html() {
        String s = StringEscapeUtils.escapeXml(value);
        return file() + s;
    }

    @Override
    public String toString() {
        return value;
    }

    public Line markError() {
        this.error = true;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        if (value != null ? !value.equals(line.value) : line.value != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public Line withThread(String thread) {
        this.thread = thread;
        return this;
    }

    public Line ofFile(String file) {
        this.file = file;
        return this;
    }

    public String getThread() {
        return thread;
    }

    @Override
    public int compareTo(Line line) {
        return this.value.compareTo(line.value);
    }

    public String file() {
        return "[" + file + "]";
    }

    public String errorTitle() {
        return errorTitle;
    }

    public String timestamp() {
        return timestamp;
    }

    public Line withTime(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void titleIs(String errorTitle) {
        this.errorTitle = errorTitle;
    }
}
