package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.web.MyServer;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
@WebClass("stop")
@Scope("singleton")
public class ShutDownController extends BaseController {

    @Autowired
    private MyServer myServer;

    @WebMethod
    public void stop(Request request, Response response) throws Exception {
        response.setValue("Content-Type", "text/html");
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("server-stop", model, response);
        myServer.stop();
    }

}
