package com.vijayrc.supportguy.web;

import com.vrc.logline.controller.AllControllers;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyContainer implements Container {
    private Executor executor;
    private AllControllers allControllers;

    @Autowired
    public MyContainer(AllControllers allControllers, @Value("#{config['server.pool.size']}") Integer poolSize) {
        this.allControllers = allControllers;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void handle(Request request, Response response) {
        HttpTask httpTask = new HttpTask(request, response, allControllers);
        this.executor.execute(httpTask);
    }
}
