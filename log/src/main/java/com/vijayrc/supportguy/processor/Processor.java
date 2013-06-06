package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.domain.Logs;

import java.text.ParseException;

public interface Processor {
    void process(Logs linePayload) throws Exception;
}
