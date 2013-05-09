package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import com.vrc.logline.controller.*;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.lang.reflect.Method;
import java.util.*;

public class AllControllers {
    private Map<String, Method> webMethods = new HashMap<String, Method>();

    private static AllControllers instance;

    public static AllControllers create() throws Exception {
        if(instance == null)
            instance = new AllControllers();
        return instance;
    }

    public AllControllers() throws Exception {
        Reflections reflections = new Reflections("com.vrc.logline.controller");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(WebClass.class);
        WebRequest webRequest;
        WebClass webClass;
        for (Class<?> aClass : classes) {
            for (Method method : aClass.getMethods()) {
                webRequest = method.getAnnotation(WebRequest.class);
                webClass = aClass.getAnnotation(WebClass.class);
                if (webRequest == null) continue;
                webMethods.put(webClass.value() + webRequest.value(), method);
            }
        }
    }

    public void act(Request request, Response response) throws Exception {
        try {
            String path = StringUtils.remove(request.getPath().toString(), "/");
            for (String key : webMethods.keySet()) {
                if (!path.contains(key)) continue;
                Method method = webMethods.get(key);
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
}
