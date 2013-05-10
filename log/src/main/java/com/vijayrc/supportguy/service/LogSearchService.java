package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.LineGroup;
import com.vrc.logline.domain.LineGroup;
import com.vrc.logline.domain.Line;
import com.vijayrc.supportguy.repository.AllLines;
import com.vijayrc.supportguy.repository.AllProcessors;
import com.vijayrc.supportguy.repository.AllRules;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

public class LogSearchService {
    private static final Logger log = Logger.getLogger(LogSearchService.class);

    private String folder;
    private AllRules allRules;
    private AllLines allLines;
    private AllProcessors allProcessors;

    public LogSearchService() {
        this.folder = folder;
        this.allLines = new AllLines();
        this.allProcessors = null; //new AllProcessors(startDate,endDate);
        this.allRules = null;//new AllRules(split(keys), option);
    }

    private List<String> split(String keys) {
        List<String> splitKeys = new ArrayList<String>();
        for (String key : StringUtils.split(keys, ","))
            splitKeys.add(StringUtils.deleteWhitespace(key));
        return splitKeys;
    }

    public void process(String keys, String folder, String startDate, String endDate, String option) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        File logDir = new File(folder);
        if (!logDir.exists()) return;
        recurse(logDir);
        watch.stop();
        log.info("Time taken to timeline: " + watch);
    }

    private void recurse(File logDir) throws Exception {
        for (File file : logDir.listFiles()) {
            if (file.isDirectory()) {
                recurse(file);
                continue;
            }
            allLines.addFileLinesFrom(file);
            allProcessors.process(allLines);
            allLines.flushFileLines();
            allRules.process(allLines);
            allLines.flushProcessedLines();
        }
    }

    public Map<String, List<Line>> keyLines() {
        return new LineGroup(allLines.keyLines()).byThread();
    }

    public Map<String, Set<Line>> errorLines() {
        return new LineGroup(allLines.errorLines()).byTitle();
    }

}
