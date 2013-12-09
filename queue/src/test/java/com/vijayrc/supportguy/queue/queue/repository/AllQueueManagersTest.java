package com.vijayrc.supportguy.queue.queue.repository;

import com.vijayrc.supportguy.queue.queue.domain.Queue;
import com.vijayrc.supportguy.queue.queue.domain.QueueManager;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
@Log4j
public class AllQueueManagersTest {

    @Autowired
    private AllQueueManagers allQueueManagers;

    @Test
    public void shouldReadYAMLLoad() throws Exception {
        List<QueueManager> all = allQueueManagers.all();
        for (QueueManager queueManager : all)    {
            queueManager.connect();
            for (Queue queue : queueManager.getQueues())
                log.info(queue.toString());
        }
        QueueManager manager = allQueueManagers.fetch("MSGDEV02D7");
        manager.connect();
    }
}