package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("home")
public class HomeController extends BaseController {
    private static final Logger log = Logger.getLogger(HomeController.class);

    @WebRequest
    public void showHome(Request request, Response response) throws Exception {
        response.setValue("Content-Type", "text/html");
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("home", model, response);
        log.info(request.getPath());
    }
}
