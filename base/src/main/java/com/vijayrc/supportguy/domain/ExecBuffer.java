package com.vijayrc.supportguy.domain;

import java.util.LinkedList;

public class ExecBuffer {
    private LinkedList<String> lines = new LinkedList<>();
    private int maxSize;

    public ExecBuffer(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(String line) {
        lines.push(line);
        if (lines.size() > maxSize) lines.removeFirst();
    }

    public String pop() {
        return lines.isEmpty() ? "No lines to buffer" : lines.pop();
    }
}
