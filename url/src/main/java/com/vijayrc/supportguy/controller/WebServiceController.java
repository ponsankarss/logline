package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@WebClass("webservice")
public class WebServiceController extends BaseController {

    @WebMethod
    public void show(Request request, Response response) throws Exception {
        renderer.render("webservice-tool", new HashMap<String, Object>(), response);
    }
}
