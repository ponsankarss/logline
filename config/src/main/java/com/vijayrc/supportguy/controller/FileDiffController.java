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
import java.util.List;
import java.util.Map;

@Component
@WebClass("config")
public class FileDiffController extends BaseController {

    @Autowired
    private FileDiffService fileDiffService;

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("scms", fileDiffService.fetchAllScms());
        model.put("machines", fileDiffService.fetchAllMachines());
        renderer.render("diff-tool", model, response);
    }

    @WebMethod("results")
    public void showResults(Request request, Response response) throws Exception {
        String machineName = request.getParameter("machine");
        String scmName = request.getParameter("scm");

        Delta delta = fileDiffService.process(machineName, scmName);

        Map<String, Object> model = new HashMap<>();
        model.put("changedFileDiffs", delta.getChanged());
        model.put("missingFileDiffs", delta.getMissing());
        renderer.render("diff-results", model, response);
    }
}
