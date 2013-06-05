package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.domain.MyMatcher;
import com.vijayrc.supportguy.repository.AllLogRegex;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j
public class TimeProcessor implements Processor {

    @Autowired
    private AllLogRegex allLogRegex;

    @Override
    public void process(Logs logs) {
        List<String> processedLines = logs.lines().processedLines();
        List<String> filteredProcessedLines = new ArrayList<String>();
        DateTime lineDate;

        for (String processedLine : processedLines) {
            MyMatcher myMatcher = allLogRegex.findMatched(processedLine);
            if (myMatcher.notMatched())
                continue;
            lineDate = myMatcher.getTime();
            if (lineDate != null && lineDate.isAfter(logs.startDate()) && lineDate.isBefore(logs.endDate()))
                filteredProcessedLines.add(processedLine);
        }
        processedLines.clear();
        processedLines.addAll(filteredProcessedLines);
    }

}
