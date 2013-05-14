package com.vijayrc.supportguy.web;

import com.vijayrc.supportguy.controller.AllControllers;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

@Log4j
public class HttpTask implements Runnable {
    private Request request;
    private Response response;
    private AllControllers allControllers;

    public HttpTask(Request request, Response response, AllControllers allControllers) {
        this.request = request;
        this.response = response;
        this.allControllers = allControllers;
    }

    @Override
    public void run() {
        try {
            allControllers.act(request, response);
        } catch (Exception e) {
            log.error(request.getPath(), e);
        }
    }
}

