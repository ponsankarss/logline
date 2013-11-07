package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.repository.AllMachines;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static com.vijayrc.supportguy.util.Util.userDir;

@Service
@Scope("prototype")
@Log4j
public class LogFetchService {
    @Autowired
    private AllMachines allMachines;

    public List<String> getFiles(String machineName, List<String> files) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (String file : files)
            buffer.append(removeTimeStamp(file) + "\\z|");

        String regex = StringUtils.removeEnd(buffer.toString(), "|");
        return allMachines.getFor(machineName).getLogFiles(regex);
    }

    public List<String> browseFiles(String machineName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        return machine.browseLogFiles();
    }

    public List<String> machineNames(){
        return allMachines.names();
    }

    private String removeTimeStamp(String logFileName) {
        return logFileName.replaceAll("\\[.*\\]", "").trim();
    }

    public List<File> dumpFolders() {
        File rootFolder = new File(userDir() + "\\logs");
        return filter(having(on(File.class).isDirectory()), rootFolder.listFiles());
    }
}
