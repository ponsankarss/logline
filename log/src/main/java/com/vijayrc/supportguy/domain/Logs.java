package com.vijayrc.supportguy.domain;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.joda.time.format.DateTimeFormat.*;

public class Logs {
    private Lines lines;
    private String option;
    private DateTime startDate;
    private DateTime endDate;
    private Pattern searchKeyPattern;

    public Logs(Lines lines, String startDateString, String endDateString, String keys, String option) {
        this.lines = lines;
        this.option = option;
        this.searchKeyPattern = keyPattern(keys);
        this.startDate = parse(startDateString);
        this.endDate = parse(endDateString);
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

    public Map<String, List<Line>> keyLines() {
        return lines.byThread();
    }

    public Map<String, Set<Line>> errorLines() {
        return lines.byError();
    }

    public String file() {
        return lines.file();
    }

    public boolean hasStartDate() {
        return startDate != null;
    }

    private DateTime parse(String date) {
        if (StringUtils.isBlank(date)) return null;
        date = date.trim();
        return date.contains(":") ?
                forPattern("MM/dd/yyyy HH:mm:ss").parseDateTime(date) :
                forPattern("MM/dd/yyyy").parseDateTime(date);
    }

    private Pattern keyPattern(String keys) {
        List<String> splitKeys = new ArrayList<String>();
        for (String key : StringUtils.split(keys, ","))
            splitKeys.add(StringUtils.deleteWhitespace(key));
        return Pattern.compile("(?i)" + StringUtils.join(splitKeys.toArray(), "|"));
    }

    public boolean hasNoSearchKey(String line) {
        return !searchKeyPattern.matcher(line).find();
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
