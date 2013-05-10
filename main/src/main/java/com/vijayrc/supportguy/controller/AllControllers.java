package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.*;

@Repository
@Scope("single")
public class AllControllers{
    private static final Logger log = Logger.getLogger(AllControllers.class);
    private final Map<String, Method> methods = new HashMap<String, Method>();

    public AllControllers() throws Exception {
        WebClass webClass;
        WebRequest webRequest;
        for (Class<?> aClass : new Reflections("com.vrc.logline.controller").getTypesAnnotatedWith(WebClass.class))
            for (Method method : aClass.getMethods()) {
                webRequest = method.getAnnotation(WebRequest.class);
                webClass = aClass.getAnnotation(WebClass.class);
                if (webRequest == null)
                    continue;
                methods.put(webClass.value() + webRequest.value(), method);
            }
        log.info("all controllers initialized");
    }

    public void act(Request request, Response response) throws Exception {
        try {
            String url = request.getPath().toString();
            log.info("serving " + url);
            String path = StringUtils.remove(url, "/");

            for (String key : methods.keySet()) {
                if (!path.contains(key)) continue;
                Method method = methods.get(key);
                addHeaders(response);
                method.invoke(method.getDeclaringClass().newInstance(), request, response);
                return;
            }
            showError(response, new Exception("Page not found, please check the url"));
        } catch (Exception e) {
            showError(response, e);
        }
    }

    private void showError(Response response, Exception exception) throws Exception {
        new ErrorController().addError(exception).showError(response);
    }

    private void addHeaders(Response response) {
        long time = System.currentTimeMillis();
        response.setValue("Server", "SupportGuy");
        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
        response.setValue("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setValue("Cache-Control", "post-check=0, pre-check=0");
        response.setValue("Pragma", "no-cache");
    }

}
