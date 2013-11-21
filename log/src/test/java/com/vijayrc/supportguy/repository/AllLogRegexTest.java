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
                "[2013-01-23 10:43:05,906] [WebContainer : 23613] INFO  com.bcbsa.blue2.web.action.SccfSearchAction -",
                "15 May 2013 14:33:58  INFO RemediationMDBBean:47 - Start",
                "[5/1/13 4:45:00:803 EDT] 00000035 SchedulerDaem W   SCHD0103W: The Scheduler Datanet Lite Scheduler (scheduler/dnlScheduler) was unable to run task 2788",
                "[11/15/13 8:43:10:112 EST] 0000001f ThreadMonitor W   WSVR0605W: Thread \"WebContainer : 3\" (0000004f) has been active for 628587 milliseconds and may be hung.  There is/are 1 thread(s) in total in the server that may be hung");
        for (String line : lines) {
            MyMatcher myMatcher = allLogRegex.findMatched(line);
            System.out.println(myMatcher.group("timestamp"));
//            if (myMatcher.hasGroup("thread"))
//                System.out.println(myMatcher.group("thread"));
//            else
//                System.out.println("no thread");
        }
    }
}
