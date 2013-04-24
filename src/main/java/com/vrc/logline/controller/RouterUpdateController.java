package com.vrc.logline.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

public class RouterUpdateController extends BaseController {
    private static final Logger log = Logger.getLogger(RouterController.class);

    public RouterUpdateController() {
        super("router-update");
    }

    @Override
    public void act(Request request, Response response) throws Exception {
        String server = request.getParameter("server");
        String port = request.getParameter("port");
        String action = request.getParameter("action");

        PostMethod postMethod = new PostMethod("http://" + server + ":" + port + "/blue2_wsrouterWeb/B2KillRouter");
        postMethod.addParameter("action", action);
        new HttpClient().executeMethod(postMethod);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", postMethod.getResponseBodyAsString());
        renderer.render("router-result", model, response);
    }
}
