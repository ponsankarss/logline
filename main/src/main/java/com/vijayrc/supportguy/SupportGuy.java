package com.vijayrc.supportguy;

import com.vijayrc.supportguy.web.MyServer;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log4j
public class
        SupportGuy {

    public static void main(String[] list) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        final MyServer myServer = (MyServer) context.getBean("myServer");
        myServer.start();
    }

}
