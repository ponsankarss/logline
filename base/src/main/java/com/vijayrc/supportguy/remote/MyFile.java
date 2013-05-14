package com.vijayrc.supportguy.remote;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class MyFile {

    private String path;

    public MyFile(String path) {
        this.path = path;
    }

    public List<String> getChildren() {
        List<String> paths = new ArrayList<String>();
        recurseFile(new File(path), paths);
        return paths;
    }

    public void recreate() throws Exception {
        File dir = new File(path);
        if (dir.exists())
            FileUtils.forceDelete(dir);
        dir.mkdir();
    }

    private void recurseFile(File target, List<String> paths) {
        for (File file : target.listFiles()) {
            if (file.isDirectory()) {
                recurseFile(file, paths);
                continue;
            }
            paths.add(file.getAbsolutePath());
        }
    }
}
