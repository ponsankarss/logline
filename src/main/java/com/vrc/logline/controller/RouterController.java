package com.vrc.logline.controller;

import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

public class RouterController extends BaseController {
    private static final Logger log = Logger.getLogger(RouterController.class);

    public RouterController() {
        super("router-view");
    }

    @Override
    public void act(Request request, Response response) throws Exception {
        log.info(request.getPath());
        addHeaders(response);
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("router", model, response);
    }
}
