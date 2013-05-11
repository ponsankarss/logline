package com.vijayrc.supportguy;

import com.vijayrc.supportguy.controller.LogController;
import com.vijayrc.supportguy.web.MyServer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SupportGuy {
    private static final Logger log = Logger.getLogger(SupportGuy.class);

    public static void main(String[] list) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        context.getBean(LogController.class);
        MyServer myServer = (MyServer) context.getBean("myServer");
        myServer.start();
        stop(myServer);
    }

    private static void stop(final MyServer myServer) {
        Thread hook = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myServer.stop();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
