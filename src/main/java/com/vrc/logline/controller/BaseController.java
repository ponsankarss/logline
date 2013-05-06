package com.vrc.logline.controller;

import com.vrc.logline.domain.Config;
import com.vrc.logline.domain.Renderer;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public abstract class BaseController{

    protected Config config = Config.get();
    protected Renderer renderer = new Renderer("/views/");

    protected void addHeaders(Response response) {
        long time = System.currentTimeMillis();
        response.setValue("Server", "Logline-VRC");
        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
        response.setValue("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setValue("Cache-Control", "post-check=0, pre-check=0");
        response.setValue("Pragma", "no-cache");
    }

}
