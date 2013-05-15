package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.util.Constants;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Component
@Log4j
public class TimeProcessor implements Processor {

    @Override
    public void process(Logs logs) {
        List<String> processedLines = logs.lines().processedLines();
        List<String> filteredProcessedLines = new ArrayList<String>();
        DateTime lineDate;

        for (String processedLine : processedLines) {
            lineDate = getLineDate(processedLine);
            if (lineDate != null && lineDate.isAfter(logs.startDate()) && lineDate.isBefore(logs.endDate()))
                filteredProcessedLines.add(processedLine);
        }
        processedLines.clear();
        processedLines.addAll(filteredProcessedLines);
    }

    //TODO
    private DateTime getLineDate(String fileLine) {
        Matcher matcher = Constants.dateRegex1.matcher(fileLine);
        if (matcher.find())
            return parse(matcher.group("timestamp"), Constants.logPattern1);
        matcher = Constants.dateRegex2.matcher(fileLine);
        if (matcher.find())
            return parse(matcher.group(), Constants.logPattern2);
        return null;
    }

    private DateTime parse(String date, String pattern) {
        if (StringUtils.isBlank(date)) return DateTime.now();
        return DateTimeFormat.forPattern(pattern).parseDateTime(date.trim());
    }
}
