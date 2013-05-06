package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import javax.jws.WebService;
import java.util.HashMap;

@WebClass("webservice")
public class WebServiceController extends BaseController {

    @WebRequest
    public void show(Request request, Response response) throws Exception {
        renderer.render("webservice-tool", new HashMap<String, Object>(), response);
    }
}
