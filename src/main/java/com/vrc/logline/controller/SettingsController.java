package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("settings")
public class SettingsController extends BaseController {

    @WebRequest("tool")
    public void show(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("settings", config.read());
        renderer.render("settings", model, response);
    }

    @WebRequest("update")
    public void update(Request request, Response response) throws Exception {
        String content = request.getParameter("content");
        config.reload(content);
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("settings-update-results", model, response);
    }
}
