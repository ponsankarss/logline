package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Delta;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.service.FileDiffService;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("config")
public class FileDiffController extends BaseController {

    @Autowired
    private FileDiffService fileDiffService;

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("diff-tool", model, response);
    }

    @WebMethod("results")
    public void showResults(Request request, Response response) throws Exception {
        String machineName = request.getParameter("machine");
        String releaseName = request.getParameter("release");

        Delta delta = fileDiffService.process(machineName, releaseName);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("changedFileDiffs", delta.getChanged());
        model.put("missingFileDiffs", delta.getMissing());
        renderer.render("diff-results", model, response);
    }
}
