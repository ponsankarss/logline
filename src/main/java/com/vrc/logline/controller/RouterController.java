package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("router")
public class RouterController extends BaseController {
    private static final Logger log = Logger.getLogger(RouterController.class);

    @WebRequest("tool")
    public void showTool(Request request, Response response) throws Exception {
        addHeaders(response);
        renderer.render("router", new HashMap<String, Object>(), response);
    }

    @WebRequest("update")
    public void updateStatus(Request request, Response response) throws Exception {
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

