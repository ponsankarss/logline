package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import com.vrc.logline.domain.NameGroup;
import com.vrc.logline.service.LogFetchService;
import com.vrc.logline.service.LogSearchService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebClass("log")
public class LogController extends BaseController {
    private static final Logger log = Logger.getLogger(LogController.class);

    @Autowired
    private LogSearchService logSearchService;
    @Autowired
    private LogFetchService logFetchService;

    @WebRequest("tool")
    public void showTool(Request request, Response response) throws Exception {
        response.setValue("Content-Type", "text/html");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("folder", System.getProperty("user.dir") + "\\logs");
        renderer.render("log-tool", model, response);
        log.info(request.getPath());
    }

    @WebRequest("browse")
    public void showFiles(Request request, Response response) throws Exception {
        String machine = request.getParameter("machine");
        List<String> logFiles = logFetchService.browseFiles(machine);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("logFileMap", new NameGroup(logFiles).byName());
        renderer.render("log-browse-results", model, response);
    }

    @WebRequest("download")
    public void downloadFiles(Request request, Response response) throws Exception {
        String machine = request.getParameter("machine");
        List<String> logFileNames = split(request.getParameter("logFileNames"));
        List<String> logNames = logFetchService.getFiles(machine, logFileNames);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("logNames", logNames);
        renderer.render("log-download-results", model, response);
    }

    @WebRequest("search")
    public void searchFiles(Request request, Response response) throws Exception {
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

    private List<String> split(String parameter) {
        return Arrays.asList(StringUtils.split(parameter, ","));
    }
}
