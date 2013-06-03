package com.vijayrc.supportguy.domain;

import org.junit.Test;

public class TailerTest {

    private Tailer tailer;

    @Test
    public void shouldStartAndStopATailer() throws Exception {
        User user = new User();
        user.setName("vichakra");
        user.setPassword("xxxxx");

        Machine machine = new Machine();
        machine.setUser(user);
        machine.setIp("nasnmasdev");

        tailer = new Tailer(machine,"/was7blue2/logs/SystemOut.log");

        Thread t = new Thread(tailer);
        t.start();

        System.out.println("sleep");
        Thread.sleep(6000);
        System.out.println("wake");
        tailer.stop();
    }
}
