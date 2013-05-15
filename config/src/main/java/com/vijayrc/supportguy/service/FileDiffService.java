package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Delta;
import com.vijayrc.supportguy.domain.FileDiff;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.Scm;
import com.vijayrc.supportguy.repository.AllMachines;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class FileDiffService {
    private AllMachines allMachines;
    private Scm scm;
    private String type;

    @Autowired
    public FileDiffService(AllMachines allMachines, Scm scm) {
        this.allMachines = allMachines;
        this.scm = scm;
        this.type = "home|scripts|.properties\\z|.xml\\z|.xsd\\z|.py\\z|.sh\\z|blue2";
    }

    //TODO
    public Delta process(String machineName, String releaseName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        List<String> machineFiles = machine.getConfigFiles(type);
        Delta delta = new Delta();

        for (String machineFile : machineFiles) {
            String relativePath = machineFile.replace(Util.userDir() + "\\config\\", "");
            String scmFile = scm.getFor(releaseName, machine.getName(), relativePath);
            delta.add(new FileDiff(relativePath, machineFile, scmFile).process());
        }
        log.info("completed processing");
        return delta;
    }

}

