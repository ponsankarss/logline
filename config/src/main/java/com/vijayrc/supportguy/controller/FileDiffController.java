package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import com.vijayrc.supportguy.service.FileDiffService;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("config")
public class FileDiffController extends BaseController {

    @WebRequest("tool")
    public void showTool(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("diff-tool", model, response);
    }

    @WebRequest("results")
    public void showResults(Request request, Response response) throws Exception {
        String machineName = request.getParameter("machine");
        String releaseName = request.getParameter("release");

        FileDiffService fileDiffService = new FileDiffService();
        fileDiffService.process(machineName, releaseName);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("changedFileDiffs", fileDiffService.changedFileDiffs());
        model.put("missingFileDiffs", fileDiffService.missingFileDiffs());
        renderer.render("diff-results", model, response);
    }
}
