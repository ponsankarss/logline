package com.vijayrc.supportguy.controller;

import ch.lambdaj.group.Group;
import com.vijayrc.supportguy.domain.Query;
import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import com.vijayrc.supportguy.service.DbService;
import lombok.extern.log4j.Log4j;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@WebClass("db")
@Log4j
public class DbController extends BaseController{

    @Autowired
    private DbService dbService;

    @WebMethod("tool")
    public void showTool(Request request, Response response) throws Exception {
        Group<Query> group = dbService.getAll();
        HashMap<String, Object> model = new HashMap<>();
        model.put("group",group);
        renderer.render("db-tool", model, response);
    }
}
