package com.vijayrc.supportguy.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * Created by vchakrav on 11/18/13.
 */
public class LogsTest {

    private Logs logs;

    @Test
    public void shouldWorkForSearchKeyPatterns() {
        logs = new Logs(null, null, null, "but has completed", "error");
        assertFalse(logs.hasNoSearchKey("[11/15/13 8:43:10:112 EST] 0000001f ThreadMonitor W   WSVR0605W: Thread \"WebContainer : 3\" (0000004f) has been active for 628587 milliseconds and may be hung.  There is/are 1 thread(s) in total in the server that may be hung."));
        assertFalse(logs.hasNoSearchKey(" server that may be hung. [11/15/13 8:44:28:852 EST] 0000004f ThreadMonitor W   WSVR0606W: Thread \"WebContainer : 3\" (0000004f) was previously reported to be hung but has completed.  It was active for approximately 707329 ms"));
    }

    @Test
    public void shouldSort() {
        List<String> boys = new ArrayList();
        boys.add("kenny");
        boys.add("cartman");
        boys.add("stan");
        boys.add("kyle");
        Collections.sort(boys);
        for (String boy : boys) {
            System.out.println(boy);
        }
    }
}
