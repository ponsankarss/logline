package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.QueueMgr;
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
public class AllQueueMgrsTest {

    @Autowired
    private AllQueueMgrs allQueueMgrs;

    @Test
    public void shouldReadYAMLLoad() throws Exception {
        List<QueueMgr> all = allQueueMgrs.all();
        for (QueueMgr queueMgr : all)
            queueMgr.connect();
    }
    @Test
    public void shouldReadYAMLLoadChannelStatus() throws Exception {
        List<QueueMgr> all = allQueueMgrs.all();
        for (QueueMgr queueMgr : all) {
            queueMgr.channelStatus();
            //queueMgr.channelDetails();
        }
    }
}