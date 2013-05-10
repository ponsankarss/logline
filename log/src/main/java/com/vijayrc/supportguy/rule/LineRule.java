package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.repository.AllLines;

public interface LineRule {

    void end();

    void start();

    void process(AllLines allLines) throws Exception;
}
