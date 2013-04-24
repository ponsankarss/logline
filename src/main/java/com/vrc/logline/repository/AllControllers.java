package com.vrc.logline.repository;

import com.vrc.logline.controller.*;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.util.ArrayList;
import java.util.List;

public class AllControllers {
    private List<Controller> controllers = new ArrayList<Controller>();

    public AllControllers() {
        controllers.add(new StaticController());
        controllers.add(new RouterController());
        controllers.add(new RouterUpdateController());
        controllers.add(new LogSearchController());
        controllers.add(new LogDownloadController());
        controllers.add(new LogToolController());
        controllers.add(new LogBrowseController());
        controllers.add(new FileDiffController());
        controllers.add(new FileDiffResultController());
        controllers.add(new SettingsUpdateController());
        controllers.add(new SettingsController());
        controllers.add(new HomeController());
    }

    public void act(Request request, Response response) throws Exception {
        try {
            for (Controller controller : controllers)
                if (controller.canTake(request)) {
                    controller.act(request, response);
                    break;
                }
        } catch (Exception e) {
            new ErrorController().addError(e).act(request,response);
        }
    }
}
