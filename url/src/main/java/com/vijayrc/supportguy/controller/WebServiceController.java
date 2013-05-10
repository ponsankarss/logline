package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;

@WebClass("webservice")
public class WebServiceController extends BaseController {

    @WebRequest
    public void show(Request request, Response response) throws Exception {
        renderer.render("webservice-tool", new HashMap<String, Object>(), response);
    }
}
