package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.FileDiff;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.Scm;
import com.vijayrc.supportguy.repository.AllMachines;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FileDiffService {
    private static final Logger log = Logger.getLogger(FileDiffService.class);
    private AllMachines allMachines = new AllMachines();

    private List<FileDiff> changedFileDiffs = new ArrayList<FileDiff>();
    private List<FileDiff> missingFileDiffs = new ArrayList<FileDiff>();

    public void process(String machineName, String releaseName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        Scm scm = new Scm();

        List<String> machineFiles = machine.getConfigFiles("home|scripts|.properties\\z|.xml\\z|.xsd\\z|.py\\z|.sh\\z|blue2");
        for (String machineFile : machineFiles) {
            String relativePath = machineFile.replace(System.getProperty("user.dir") + "\\config\\", "");
            String cvsFile = scm.getFor(releaseName, machine.getName(), relativePath);
            FileDiff fileDiff = new FileDiff(relativePath, machineFile, cvsFile).process();
            if (fileDiff.isMissing())
                missingFileDiffs.add(fileDiff);
            else
                changedFileDiffs.add(fileDiff);
        }
        log.info("completed processing");
    }

    public List<FileDiff> changedFileDiffs() {
        return changedFileDiffs;
    }

    public List<FileDiff> missingFileDiffs() {
        return missingFileDiffs;
    }
}

