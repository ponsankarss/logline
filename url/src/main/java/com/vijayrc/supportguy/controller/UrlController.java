package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("router")
public class UrlController extends BaseController {

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        renderer.render("router", new HashMap<String, Object>(), response);
    }

    @WebMethod("update")
    public void post(Request request, Response response) throws Exception {
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

