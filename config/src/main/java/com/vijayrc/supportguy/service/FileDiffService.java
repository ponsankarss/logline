package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Delta;
import com.vijayrc.supportguy.domain.FileDiff;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.Scm;
import com.vijayrc.supportguy.repository.AllMachines;
import com.vijayrc.supportguy.repository.AllScms;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class FileDiffService {
    private AllMachines allMachines;
    private AllScms allScms;

    @Autowired
    public FileDiffService(AllMachines allMachines, AllScms allScms) {
        this.allMachines = allMachines;
        this.allScms = allScms;
    }

    public Delta process(String machineName, String scmName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        Scm scm = allScms.getFor(scmName);

        List<String> machineFiles = machine.getConfigFiles(scm.getType());
        Delta delta = new Delta();

        for (String machineFile : machineFiles) {
            String scmFile = scm.getFileFor(machineFile);
            delta.add(new FileDiff(machineFile, scmFile).process());
        }
        log.info("completed processing");
        return delta;
    }

}

