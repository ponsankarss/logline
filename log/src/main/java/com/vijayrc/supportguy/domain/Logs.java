package com.vijayrc.supportguy.domain;

import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.split;
import static org.joda.time.format.DateTimeFormat.forPattern;

@Log4j
public class Logs {
    private Lines lines;
    private String option;
    private DateTime startDate;
    private DateTime endDate;
    private String[] searchKeys;

    public Logs(Lines lines, String startDateString, String endDateString, String keys, String option) {
        this.lines = lines;
        this.option = option;
        this.startDate = parse(startDateString);
        this.endDate = parse(endDateString);
        this.searchKeys = split(keys, ",");
    }

    public Lines lines() {
        return lines;
    }

    public DateTime startDate() {
        return startDate;
    }

    public DateTime endDate() {
        return endDate;
    }

    public Map<String, List<Line>> errorLines() {
        return lines.byError();
    }

    public String file() {
        return lines.file();
    }

    public boolean hasStartDate() {
        return startDate != null;
    }

    private DateTime parse(String date) {
        if (isBlank(date)) return null;
        date = date.trim();
        return date.contains(":") ?
                forPattern("MM/dd/yyyy HH:mm:ss").parseDateTime(date) :
                forPattern("MM/dd/yyyy").parseDateTime(date);
    }

    public boolean hasNoSearchKey(String line) {
        for (String searchKey : searchKeys)
            if(line.contains(searchKey))return false;
        return true;
    }

    public void addFileLinesFrom(File file) throws Exception {
        lines.addFileLinesFrom(file);
    }

    public void flushFileLines() {
        lines.flushFileLines();
    }

    public void flushProcessedLines() {
        lines.flushProcessedLines();
    }

    public String option() {
        return option;
    }
}
