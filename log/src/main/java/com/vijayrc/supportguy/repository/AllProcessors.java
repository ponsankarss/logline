package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.processor.ExceptionProcessor;
import com.vijayrc.supportguy.processor.Processor;
import com.vijayrc.supportguy.processor.TimelineProcessor;
import com.vijayrc.supportguy.processor.XMLProcessor;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

import static java.util.Arrays.asList;

public class AllProcessors {
    private static final Logger log = Logger.getLogger(AllProcessors.class);
    private List<Processor> processors;

    public AllProcessors(String startDate, String endDate) {
        ExceptionProcessor exceptionProcessor = new ExceptionProcessor();
        TimelineProcessor timelineProcessor = new TimelineProcessor(startDate, endDate);
        XMLProcessor xmlProcessor = new XMLProcessor();

        processors = StringUtils.isBlank(startDate) ? asList(xmlProcessor, exceptionProcessor)
                : asList(timelineProcessor, xmlProcessor, exceptionProcessor);
    }

    public void process(AllLines allLines) {
        for (Processor processor : processors)
            processor.process(allLines);
        log.info("processing done: " + allLines.file());
    }

}
