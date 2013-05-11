package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.domain.Logs;

public interface Processor {
    void process(Logs linePayload);
}
