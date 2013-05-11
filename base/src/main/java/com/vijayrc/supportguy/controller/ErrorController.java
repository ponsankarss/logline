package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@WebClass("error")
public class ErrorController extends BaseController {

    @WebMethod
    public void showError(Response response, Exception exception) throws Exception {
        String errorString = exception != null ? ExceptionUtils.getFullStackTrace(exception) : "Error occurred. Please check the logs";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("error", errorString);
        renderer.render("error", model, response);
    }
}
