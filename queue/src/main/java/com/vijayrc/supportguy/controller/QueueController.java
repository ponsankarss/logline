package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Queue;
import com.vijayrc.supportguy.domain.QueueMgr;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.repository.AllQueueMgrs;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    @WebMethod("connect")
    public void connect(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        String name = request.getParameter("queueMgr");

        QueueMgr queueMgr = allQueueMgrs.fetch(name);
        queueMgr.connect();

        model.put("queueMgr", queueMgr);
        renderer.render("queue-mgr", model, response);
    }

    @WebMethod("browse")
    public void browse(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        String queueMgrName = request.getParameter("queueMgrName");
        String queueName = request.getParameter("queueName");

        QueueMgr queueMgr = allQueueMgrs.fetch(queueMgrName);
        Queue queue = queueMgr.browse(queueName);

        model.put("queue", queue);
        model.put("queueMgr", queueMgr);
        renderer.render("queue-browse", model, response);
    }

    @WebMethod("channels")
    public void channels(Request request, Response response) throws Exception {
        Map<String, Object> model = new TreeMap<>();
        String queueMgrName = request.getParameter("queueMgrName");

        QueueMgr queueMgr = allQueueMgrs.fetch(queueMgrName);

        model.put("queueMgr", queueMgr);
        model.put("channelMap", queueMgr.channelStatus());
        renderer.render("queue-channels", model, response);
    }
}
