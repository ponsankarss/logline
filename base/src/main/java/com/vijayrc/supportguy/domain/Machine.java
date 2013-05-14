package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.meta.ConfigUnit;
import com.vijayrc.supportguy.remote.MyFile;
import com.vijayrc.supportguy.remote.MyJsch;
import com.vijayrc.supportguy.remote.MyRemote;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

public class Machine {
    private static final Logger log = Logger.getLogger(Machine.class);

    private String name;
    private String ip;
    private String logDir;
    private String configDir;

    @ConfigUnit(User.class)
    private User user;

    private Pattern logPattern = Pattern.compile("[\\w]+.log(.\\d)*");

    public Machine() {
    }

    public Machine(String name) {
        this.name = name;
    }

    public List<String> browseLogFiles() throws Exception {
        return remote().browse(logDir, logPattern);
    }

    public List<String> getLogFiles(String type) throws Exception {
        String downloadDir = getUserDir() + "/logs/";
        remote().download(logDir, downloadDir, false, pattern(type));
        log.info("all log files downloaded");
        return new MyFile(downloadDir).getChildren();
    }

    public List<String> getConfigFiles(String type) throws Exception {
        String downloadDir = getUserDir() + "/config/";
        remote().download(configDir, downloadDir, true, pattern(type));
        log.info("all config files downloaded");
        return new MyFile(downloadDir).getChildren();
    }

    public boolean nameIs(String name) {
        return this.name.equals(name);
    }

    public String name() {
        return name;
    }

    public String ip() {
        return ip;
    }

    public String user() {
        return user.getLoginName();
    }

    public String password() {
        return user.getLoginPassword();
    }

    private String getUserDir() {
        return System.getProperty("user.dir");
    }

    private MyRemote remote() {
        return new MyJsch(this);
    }

    private Pattern pattern(String type) {
        if (StringUtils.isBlank(type)) return Pattern.compile(".*");
        return Pattern.compile(type.replaceAll(",", "|"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
                                                                              