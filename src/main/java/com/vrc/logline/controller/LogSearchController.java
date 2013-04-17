package com.vrc.logline.controller;

import com.vrc.logline.service.LogSearchService;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.HashMap;
import java.util.Map;

public class LogSearchController extends BaseController implements Controller {
    private static final Logger log = Logger.getLogger(LogSearchController.class);

    public LogSearchController() {
        super("analyse");
    }

    @Override
    public void act(Request request, Response response) throws Exception {
        addHeaders(response);
        log.info(request.getPath());
        String keys = request.getParameter("keys");
        String folder = request.getParameter("folder");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String option = request.getParameter("option");

        log.info("keys=" + keys + "|folder=" + folder + "|startDate=" + startDate + "|endDate=" + endDate+"|option="+option);

        LogSearchService logSearchService = new LogSearchService(keys, folder, startDate, endDate, option);
        logSearchService.process();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("keyLines", logSearchService.keyLines());
        model.put("errorLines", logSearchService.errorLines());
        renderer.render("log-search-results", model, response);
    }
}
