package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.repository.AllMachines;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

public class LogFetchService {
    private static final Logger log = Logger.getLogger(LogFetchService.class);

    private AllMachines allMachines = new AllMachines();

    public LogFetchService() {
        this.allMachines = new AllMachines();
    }

    public List<String> getFiles(String machineName, List<String> logFileNames) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (String logFileName : logFileNames)
            buffer.append(logFileName.replaceAll("\\[.*\\]", "").trim() + "\\z|");

        String regex = StringUtils.removeEnd(buffer.toString(), "|");
        log.info("selected logs regex: " + regex);
        return allMachines.getFor(machineName).getLogFiles(regex);
    }

    public List<String> browseFiles(String machineName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        return machine.browseLogFiles();
    }
}
