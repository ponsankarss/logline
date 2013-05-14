package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.repository.AllMachines;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
@Log4j
public class LogFetchService {
    @Autowired
    private AllMachines allMachines;

    public List<String> getFiles(String machineName, List<String> files) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (String file : files)
            buffer.append(file.replaceAll("\\[.*\\]", "").trim() + "\\z|");

        String regex = StringUtils.removeEnd(buffer.toString(), "|");
        return allMachines.getFor(machineName).getLogFiles(regex);
    }

    public List<String> browseFiles(String machineName) throws Exception {
        Machine machine = allMachines.getFor(machineName);
        return machine.browseLogFiles();
    }
}
