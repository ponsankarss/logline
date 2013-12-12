package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.repository.AllQueueMgrs;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@WebClass("queue")
@Log4j
public class QueueController extends BaseController {

    @Autowired
    private AllQueueMgrs allQueueMgrs;

    @WebMethod("tool")
    public void tool(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        model.put("queueMgrs", allQueueMgrs.all());
        renderer.render("queue-tool", model, response);
    }
}
