package com.vrc.logline.domain;

import com.vrc.logline.controller.AllControllers;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public class HttpTask implements Runnable {
    private static final Logger log = Logger.getLogger(HttpTask.class);
    private final Request request;
    private final Response response;

    public HttpTask(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            AllControllers.create().act(request, response);
        } catch (Exception e) {
            log.error(request.getPath(), e);
        }
    }
}

