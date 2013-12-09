package com.vijayrc.supportguy.queue.queue.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.queue.queue.domain.Queue;
import com.vijayrc.supportguy.queue.queue.domain.QueueManager;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
@Log4j
public class AllQueueManagers {
    private List<QueueManager> managers = new ArrayList<>();

    public AllQueueManagers() throws Exception {
        String file = Util.resource("queues.yml");
        String fileContent =
                FileUtils.readFileToString(new File(file))
                        .replaceAll("qmgr::", "!" + QueueManager.class.getName())
                        .replaceAll("q::", "!" + Queue.class.getName());
        StringReader stringReader = new StringReader(fileContent);
        YamlReader reader = new YamlReader(stringReader);
        while (true) {
            QueueManager manager = reader.read(QueueManager.class);
            if (manager == null) break;
            managers.add(manager);
            log.info(manager);
        }
    }

    public List<QueueManager> all() {
        return managers;
    }

    public QueueManager fetch(String name) {
        for (QueueManager manager : managers)
            if(manager.getName().equals(name))
                return manager;
        return null;
    }


}
