package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.domain.Logs;

public interface LineRule {
    void process(Logs logs) throws Exception;
}
