package com.vijayrc.supportguy.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by vchakrav on 11/18/13.
 */
public class LogsTest {

    private Logs logs;

    @Test
    public void shouldWorkForSearchKeyPatterns(){
       logs = new Logs(null,null,null,"has been active,vijay","error");
       assertFalse(logs.hasNoSearchKey("[11/15/13 8:43:10:112 EST] 0000001f ThreadMonitor W   WSVR0605W: Thread \"WebContainer : 3\" (0000004f) has been active for 628587 milliseconds and may be hung.  There is/are 1 thread(s) in total in the server that may be hung."));
    }
}
