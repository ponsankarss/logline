package com.vrc.logline.domain;

import com.vrc.logline.repository.AllLines;
import com.vrc.logline.repository.AllProcessors;
import com.vrc.logline.repository.AllRules;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

public class TimeLine {
    private static final Logger log = Logger.getLogger(TimeLine.class);
    private String folder;
    private AllRules allRules;
    private AllLines allLines;
    private AllProcessors allProcessors;

    public TimeLine(String keys, String folder) {
        this.folder = folder;
        this.allLines = new AllLines();
        this.allProcessors = new AllProcessors();
        this.allRules = new AllRules(split(keys));
    }

    private List<String> split(String keys) {
        List<String> splitKeys = new ArrayList<String>();
        for (String key : StringUtils.split(keys, ","))
            if (StringUtils.isNotBlank(key))
                splitKeys.add(key.trim());
        return splitKeys;
    }

    public void process() throws Exception {
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
            if (file.isDirectory()) recurse(file);
            allLines.addFileLinesFrom(file);
            allProcessors.process(allLines);
            allLines.flushFileLines();
            allRules.process(allLines);
            allLines.flushProcessedLines();
        }
    }

    public Map<String, List<Line>> keyLines() {
        return new LineGroup(allLines.keyLines()).strip().byThread();
    }

    public Set<Line> errorLines() {
        return allLines.errorLines();
    }

}
