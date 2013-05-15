package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.NameGroup;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.service.LogFetchService;
import com.vijayrc.supportguy.service.LogSearchService;

import static com.vijayrc.supportguy.util.Util.*;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@WebClass("log")
@Log4j
public class LogController extends BaseController {
    @Autowired
    private LogSearchService searchService;
    @Autowired
    private LogFetchService fetchService;

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("folder", userDir() + "\\logs");
        model.put("machines",fetchService.machineNames());
        renderer.render("log-tool", model, response);
    }

    @WebMethod("browse")
    public void browse(Request request, Response response) throws Exception {
        String machine = request.getParameter("machine");
        List<String> logFiles = fetchService.browseFiles(machine);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("logFileMap", new NameGroup(logFiles).byName());
        renderer.render("log-browse-results", model, response);
    }

    @WebMethod("download")
    public void download(Request request, Response response) throws Exception {
        String machine = request.getParameter("machine");
        List<String> logFileNames = Arrays.asList(StringUtils.split(request.getParameter("logFileNames"), ","));
        List<String> logNames = fetchService.getFiles(machine, logFileNames);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("logNames", logNames);
        renderer.render("log-download-results", model, response);
    }

    @WebMethod("search")
    public void search(Request request, Response response) throws Exception {
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

}
