package com.vijayrc.supportguy.web;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Service
@Scope("singleton")
public class Renderer {
    private VelocityEngine velocityEngine;

    @Autowired
    public Renderer() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getCanonicalName());
        velocityEngine.setProperty(VelocityEngine.RUNTIME_REFERENCES_STRICT, true);
        velocityEngine.init();
    }

    public void render(String viewName, Map<String, Object> model, Response response) throws IOException {
        VelocityContext context = new VelocityContext(model);
        Writer writer = new OutputStreamWriter(response.getPrintStream());
        velocityEngine.mergeTemplate("/views/" + viewName + ".vm", "UTF-8", context, writer);
        writer.close();
        response.close();
    }
}
