package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.processor.ErrorProcessor;
import com.vijayrc.supportguy.processor.Processor;
import com.vijayrc.supportguy.processor.TimeProcessor;
import com.vijayrc.supportguy.processor.XMLProcessor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

@Repository
@Log4j
public class AllProcessors {
    @Autowired
    private ErrorProcessor errorProcessor;
    @Autowired
    private TimeProcessor timeProcessor;
    @Autowired
    private XMLProcessor xmlProcessor;

    public void process(Logs logs) {
        List<Processor> processors = logs.hasStartDate()
                ? asList(xmlProcessor, errorProcessor, timeProcessor)
                : asList(xmlProcessor, errorProcessor);

        for (Processor processor : processors)
            processor.process(logs);
        log.info("processing done: " + logs.file());
    }

}
