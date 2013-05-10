package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

@WebClass("static")
public class StaticController extends BaseController {
    private static final Logger log = Logger.getLogger(StaticController.class);

    @WebRequest
    public void show(Request request, Response response) throws Exception {
        String path = request.getPath().toString();
        String fileName = StringUtils.substringAfter(path, "static");
        String directory = "static";//config.runMode() ? config.userDir() : config.getPath("static");
        File file = new File(directory + fileName);
        if (!file.exists()) return;

        if (path.contains("images")) {
            response.setValue("Content-Type", "image/GIF");
            OutputStream out = response.getOutputStream();
            ImageIO.write(ImageIO.read(file), "gif", out);
            out.close();
        } else {
            response.setValue("Content-Type", "text/plain");
            Writer writer = new OutputStreamWriter(response.getPrintStream());
            writer.write(FileUtils.readFileToString(file));
            writer.close();
        }
        log.info(request.getPath());
    }
}
