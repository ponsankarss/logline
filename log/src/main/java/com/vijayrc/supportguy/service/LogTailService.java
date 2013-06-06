package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.Tailer;
import com.vijayrc.supportguy.repository.AllMachines;
import com.vijayrc.supportguy.repository.AllTailers;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class LogTailService {

    @Autowired
    private AllMachines allMachines;

    @Autowired
    private AllTailers allTailers;

    public List<String> startTailing(String machineName, List<String> logFileNames) {
        Machine machine = allMachines.getFor(machineName);
        for (String logFileName : logFileNames) {
            Tailer tailer = new Tailer(machine, machine.getLogDir() + "/" + logFileName, 30);
            Thread t = new Thread(tailer);
            t.start();
            allTailers.add(tailer);
        }
        return allTailers.allNames();
    }

    public String pullTail(String tailName) {
        Tailer tailer = allTailers.find(tailName);
        return tailer != null ? tailer.pop() : tailName + " not present";
    }

    public String stopTail(String tailName) throws Exception {
        Tailer tailer = allTailers.find(tailName);
        if (tailer == null)
            return "No tailer present";
        tailer.stop();
        return tailName + " stopped";
    }
}
