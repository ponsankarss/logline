package com.vijayrc.supportguy.controller;

import com.vijayrc.supportguy.meta.WebClass;
import com.vijayrc.supportguy.meta.WebMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

@Component
@WebClass("static")
public class StaticController extends BaseController {

    @WebMethod
    public void show(Request request, Response response) throws Exception {
        String path = request.getPath().toString();
        String fileName = StringUtils.substringAfter(path, "static");
        String directory = getPath("static");
        File file = new File(directory + fileName);
        if (!file.exists()) return;

        if (path.contains("images")) {
            String contentType = path.contains("gif") ?"image/GIF":"image/PNG";
            response.setValue("Content-Type", contentType);
            OutputStream out = response.getOutputStream();
            ImageIO.write(ImageIO.read(file), "gif", out);
            out.close();
        } else {
            response.setValue("Content-Type", "text/plain");
            Writer writer = new OutputStreamWriter(response.getPrintStream());
            writer.write(FileUtils.readFileToString(file));
            writer.close();
        }
    }

    public String getPath(String path) {
        return ClassLoader.getSystemResource(path).getFile();
    }

}
