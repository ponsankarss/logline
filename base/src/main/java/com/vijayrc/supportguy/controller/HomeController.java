package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("home")
public class HomeController extends BaseController {

    @WebMethod
    public void showHome(Request request, Response response) throws Exception {
        response.setValue("Content-Type", "text/html");
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("home", model, response);
    }
}
