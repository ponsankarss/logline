package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("error")
public class ErrorController extends BaseController {
    private static final Logger log = Logger.getLogger(ErrorController.class);

    private Exception exception;

    public ErrorController addError(Exception exception) {
        log.error(exception);
        this.exception = exception;
        return this;
    }

    @WebRequest
    public void showError(Response response) throws Exception {
        addHeaders(response);
        String errorString = exception != null ? ExceptionUtils.getFullStackTrace(exception) : "Error occurred. Please check the logs";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("error", errorString);
        renderer.render("error", model, response);
    }
}
