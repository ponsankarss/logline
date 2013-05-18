package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.MyMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class AllLogRegexTest {

    @Autowired
    private AllLogRegex allLogRegex;

    @Test
    public void shouldMatchRightRegexAgainstLinesAndExtractGroups() {
        List<String> lines = asList(
                "15 May 2013 14:33:58  INFO RemediationMDBBean:47 - Start",
                "[5/15/13 12:19:04:397 EDT] 0000003d PmiRmArmWrapp I   PMRM0003I:  parent:ver=1,ip=10.201.114.65,time=1368562670017,pid=14286868,reqid=45091",
                "[5/1/13 4:45:00:803 EDT] 00000035 SchedulerDaem W   SCHD0103W: The Scheduler Datanet Lite Scheduler (scheduler/dnlScheduler) was unable to run task 2788");
        for (String line : lines) {
            MyMatcher myMatcher = allLogRegex.findMatched(line);
            System.out.println(myMatcher + "|" + myMatcher.group("timestamp"));
            if (myMatcher.hasGroup("thread"))
                System.out.println(myMatcher.group("thread"));
            else
                System.out.println("no thread");
        }
    }
}
