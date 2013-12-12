package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.domain.LinkHit;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.service.LinkService;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("link")
@Log4j
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;

    @WebMethod("tool")
    public void tool(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        model.put("map", linkService.getAll());
        renderer.render("link-tool", model, response);
    }

    @WebMethod("hit")
    public void post(Request request, Response response) throws Exception {
        String linkName = request.getParameter("link");
        String environment = request.getParameter("env");

        Link link = linkService.getFor(linkName, environment);
        log.info("processing:" + link);
        if (link.hasParams())
            for (String param : link.getParams())
                link.addParam(param, request.getParameter(param));

        LinkHit linkHit = linkService.process(link);
        log.info(linkHit);
        Map<String, Object> model = new HashMap<>();
        model.put("linkHit", linkHit);
        renderer.render("link-result", model, response);
    }
}

