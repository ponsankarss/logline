package com.vrc.logline.repository;

import com.vrc.logline.processor.ExceptionProcessor;
import com.vrc.logline.processor.Processor;
import com.vrc.logline.processor.TimelineProcessor;
import com.vrc.logline.processor.XMLProcessor;
import difflib.StringUtills;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
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
