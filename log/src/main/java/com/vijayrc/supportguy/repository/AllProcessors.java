package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.processor.ErrorProcessor;
import com.vijayrc.supportguy.processor.Processor;
import com.vijayrc.supportguy.processor.TimeProcessor;
import com.vijayrc.supportguy.processor.XMLProcessor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Arrays.asList;

public class AllProcessors {
    private static final Logger log = Logger.getLogger(AllProcessors.class);

    @Autowired
    private ErrorProcessor errorProcessor;
    @Autowired
    private TimeProcessor timeProcessor;
    @Autowired
    private XMLProcessor xmlProcessor;

    public void process(Logs logs) {
        List<Processor> processors = logs.hasStartDate()
                ? asList(timeProcessor, xmlProcessor, errorProcessor)
                : asList(xmlProcessor, errorProcessor);

        for (Processor processor : processors)
            processor.process(logs);
        log.info("processing done: " + logs.file());
    }

}
