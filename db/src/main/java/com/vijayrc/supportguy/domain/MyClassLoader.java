package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.remote.MyFile;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

import java.net.URL;
import java.net.URLClassLoader;

@Log4j
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(URL[] urls) throws Exception {
        super(urls);
        for (String jarPath : new MyFile(Util.userDir() + "/jdbc").getChildren()) {
            jarPath = StringUtils.replace(jarPath, "\\", "/");
            String prefix = SystemUtils.IS_OS_WINDOWS ? "jar:file:/" : "jar:file:";
            String urlPath = prefix + jarPath + "!/";

            log.info("loading drivers from :" + urlPath);
            addURL(new URL(urlPath));
        }
    }

}
