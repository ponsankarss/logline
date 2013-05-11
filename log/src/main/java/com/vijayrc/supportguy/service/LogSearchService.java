package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.repository.AllProcessors;
import com.vijayrc.supportguy.repository.AllRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Scope("prototype")
public class LogSearchService {

    @Autowired
    private AllRules allRules;
    @Autowired
    private AllProcessors allProcessors;

    public Lines process(String keys, String folder, String startDate, String endDate, String option) throws Exception {
        Logs logs = new Logs(new Lines(), startDate, endDate, keys, option);
        File logDir = new File(folder);
        if (!logDir.exists())
            return logs.lines();
        recurse(logDir, logs);
        return logs.lines();
    }

    private void recurse(File logDir, Logs logs) throws Exception {
        for (File file : logDir.listFiles()) {
            if (file.isDirectory()) {
                recurse(file, logs);
                continue;
            }
            logs.addFileLinesFrom(file);
            allProcessors.process(logs);
            logs.flushFileLines();
            allRules.process(logs);
            logs.flushProcessedLines();
        }
    }


}
