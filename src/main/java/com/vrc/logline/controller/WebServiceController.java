package com.vrc.logline.controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;

public class WebServiceController extends BaseController {

    public WebServiceController() {
        super("webservice");
    }

    @Override
    public void act(Request request, Response response) throws Exception {
        renderer.render("webservice-tool", new HashMap<String, Object>(), response);
    }
}
