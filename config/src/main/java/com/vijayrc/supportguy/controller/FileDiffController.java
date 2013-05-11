package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.service.FileDiffService;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@WebClass("config")
public class FileDiffController extends BaseController {

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("diff-tool", model, response);
    }

    @WebMethod("results")
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
