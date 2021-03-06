package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Repository
@Log4j
public class AllControllers implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private Map<String, ControllerMethod> methods = new TreeMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!bean.getClass().isAnnotationPresent(WebClass.class))
            return bean;
        for (Method method : bean.getClass().getMethods()) {
            if (!method.isAnnotationPresent(WebMethod.class))
                continue;
            String key = bean.getClass().getAnnotation(WebClass.class).value() + method.getAnnotation(WebMethod.class).value();
            methods.put(StringUtils.remove(key, "/"), new ControllerMethod(bean, method));
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void act(Request request, Response response) throws Exception {
        try {
            String url = request.getPath().toString();
            String path = StringUtils.remove(url, "/");
            log.info("serving " + url);

            for (String key : methods.keySet()) {
                if (!path.contains(key)) continue;
                addHeaders(response);
                ControllerMethod controllerMethod = methods.get(key);
                controllerMethod.method.invoke(controllerMethod.bean, request, response);
                return;
            }
            showError(response, new Exception("Page not found, please check the url"));
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            showError(response, e);
        }
    }

    private void showError(Response response, Exception exception) throws Exception {
        new ErrorController().showError(response, exception);
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (String urlKey : methods.keySet())
            log.info(urlKey);
    }

    private class ControllerMethod {
        private Object bean;
        private Method method;

        private ControllerMethod(Object bean, Method method) {
            this.bean = bean;
            this.method = method;
        }
    }
}
