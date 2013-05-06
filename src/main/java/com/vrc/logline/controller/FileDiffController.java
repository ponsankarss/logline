package com.vrc.logline.controller;

import com.vrc.logline.container.WebClass;
import com.vrc.logline.container.WebRequest;
import com.vrc.logline.service.FileDiffService;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

@WebClass("config")
public class FileDiffController extends BaseController {
    private static final Logger log = Logger.getLogger(FileDiffController.class);

    @WebRequest("tool")
    public void showTool(Request request, Response response) throws Exception {
        log.info(request.getPath());
        addHeaders(response);
        Map<String, Object> model = new HashMap<String, Object>();
        renderer.render("diff-tool", model, response);
    }

    @WebRequest("results")
    public void showResults(Request request, Response response) throws Exception {
        log.info(request.getPath());
        addHeaders(response);
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
