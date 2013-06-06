package com.vijayrc.supportguy.domain;

import org.junit.Test;

public class TailerTest {

    private Tailer tailer;

    @Test
    public void shouldStartAndStopATailer() throws Exception {
        User user = new User();
        user.setName("vichakra");
        user.setPassword("tindrum210");

        Machine machine = new Machine();
        machine.setUser(user);
        machine.setIp("nasnmasdev");

        tailer = new Tailer(machine,"/was7blue2/logs/b2_router.log", 30);
        Thread t = new Thread(tailer);
        t.start();

        //inquire
        while(true){
            System.out.println(tailer.pop());
            Thread.sleep(1000);
        }

//        //to stop
//        System.out.println("sleep");
//        Thread.sleep(6000);
//        System.out.println("wake");
//        tailer.stop();
    }
}
