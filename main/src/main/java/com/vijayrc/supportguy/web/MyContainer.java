package com.vijayrc.supportguy.web;

import com.vijayrc.supportguy.controller.AllControllers;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Scope("singleton")
@Log4j
public class MyContainer implements Container {
    private Executor executor;
    private AllControllers allControllers;

    @Autowired
    public MyContainer(AllControllers allControllers, @Value("#{config['server.pool.size']}") Integer poolSize) {
        this.allControllers = allControllers;
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public void handle(Request request, Response response) {
        HttpTask httpTask = new HttpTask(request, response, allControllers);
        this.executor.execute(httpTask);
    }

}
