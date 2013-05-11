package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.NameGroup;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import com.vijayrc.supportguy.service.LogFetchService;
import com.vijayrc.supportguy.service.LogSearchService;
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
    private LogSearchService searchService;
    @Autowired
    private LogFetchService fetchService;

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
        List<String> logFiles = fetchService.browseFiles(machine);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("logFileMap", new NameGroup(logFiles).byName());
        renderer.render("log-browse-results", model, response);
    }

    @WebRequest("download")
    public void downloadFiles(Request request, Response response) throws Exception {
        String machine = request.getParameter("machine");
        List<String> logFileNames = split(request.getParameter("logFileNames"));
        List<String> logNames = fetchService.getFiles(machine, logFileNames);

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
        log.info("keys=" + keys + "|folder=" + folder + "|startDate=" + startDate + "|endDate=" + endDate + "|option=" + option);

        Lines lines = searchService.process(keys, folder, startDate, endDate, option);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("keyLines", lines.keyLines());
        model.put("errorLines", lines.errorLines());
        renderer.render("log-search-results", model, response);
    }

    private List<String> split(String parameter) {
        return Arrays.asList(StringUtils.split(parameter, ","));
    }
}
