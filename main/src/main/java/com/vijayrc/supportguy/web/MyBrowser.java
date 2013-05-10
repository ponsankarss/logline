package com.vijayrc.supportguy.web;

import org.apache.log4j.Logger;

import java.awt.*;
import java.net.URI;

public class MyBrowser {
    private static final Logger log = Logger.getLogger(MyBrowser.class);
    private final String url = "http://localhost:8080/";

    public void start() throws Exception {
        Desktop.getDesktop().browse(URI.create(url));
        log.info("Browser opened");

    }
}
