package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.repository.AllLinks;
import com.vijayrc.supportguy.service.LinkService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("link")
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        model.put("linkGroup",linkService.getAll());
        renderer.render("link-tool", model, response);
    }

    @WebMethod("update")
    public void post(Request request, Response response) throws Exception {
        String linkName = request.getParameter("link");
        Link link = linkService.getFor(linkName);
        for (String param : link.getParams())
                link.addParam(param,request.getParameter(param));

        String result = linkService.process(link);
        Map<String, Object> model = new HashMap<>();
        model.put("result",result);
        renderer.render("router-result", model, response);
    }
}

