package com.vijayrc.supportguy.domain;

import org.apache.log4j.Logger;

public class Scm {
    private static final Logger log = Logger.getLogger(Scm.class);

    public String getFor(String release, String environment, String path) {
//        String relativePath = path.replace(config.userDir() + "\\config\\", "");
//        String cvsFilePath = config.userCvsDir() + "/" + release + "/configuration/" + environment + "/" + relativePath;
//        return cvsFilePath;
        return "";
    }
}
