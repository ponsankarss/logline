package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.repository.AllLines;
import com.vrc.logline.domain.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class TimelineProcessor implements Processor {
    private static final Logger log = Logger.getLogger(TimelineProcessor.class);

    private DateTime startDate;
    private DateTime endDate;

    private final String pattern1 = "MM/dd/yyyy HH:mm:ss";
    private final String pattern2 = "yyyy-MM-dd HH:mm:ss,SSS";
    private final String pattern3 = "dd MMM yyyy HH:mm:ss";
    private final String pattern4 = "MM/dd/yyyy";

    public TimelineProcessor(String startDate, String endDate) {

    }

    @Override
    public void process(AllLines allLines, String startDateString, String endDateString) {
        DateTime startDate = parse(startDateString, pattern1);
        DateTime endDate = parse(endDateString, pattern1);

        DateTime lineDate;
        List<String> processedLines = allLines.processedLines();
        List<String> filteredProcessedLines = new ArrayList<String>();
        for (String processedLine : processedLines) {
            lineDate = getLineDate(processedLine);
            if (lineDate != null && lineDate.isAfter(startDate) && lineDate.isBefore(endDate))
                filteredProcessedLines.add(processedLine);
        }
        log.info("Time Filter took " + filteredProcessedLines.size() + " out of " + processedLines.size());
        processedLines.clear();
        processedLines.addAll(filteredProcessedLines);
    }

    private DateTime getLineDate(String fileLine) {
        Matcher matcher = config.datePattern1().matcher(fileLine);
        if (matcher.find())
            return parse(matcher.group("timestamp"), pattern2);
        matcher = config.datePattern2().matcher(fileLine);
        if (matcher.find())
            return parse(matcher.group(), pattern3);
        return null;
    }

    private DateTime parse(String date, String pattern) {
        if (StringUtils.isBlank(date)) return DateTime.now();
        date = date.trim();
        return date.contains(":") ? DateTimeFormat.forPattern(pattern).parseDateTime(date)
                : DateTimeFormat.forPattern(pattern4).parseDateTime(date);
    }
}
