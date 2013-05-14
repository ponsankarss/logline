package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.remote.MyFile;
import com.vijayrc.supportguy.remote.MyJsch;
import com.vijayrc.supportguy.remote.MyRemote;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
@Data
@Log4j
public class Machine {
    private String name;
    private String ip;
    private String logDir;
    private String configDir;
    private User user;
    private Pattern logPattern = Pattern.compile("[\\w]+.log(.\\d)*");

    public List<String> browseLogFiles() throws Exception {
        return remote().browse(logDir, logPattern);
    }

    public List<String> getLogFiles(String type) throws Exception {
        String downloadDir = userDir() + "/logs/";
        remote().download(logDir, downloadDir, false, pattern(type));
        log.info("all log files downloaded");
        return new MyFile(downloadDir).getChildren();
    }

    public List<String> getConfigFiles(String type) throws Exception {
        String downloadDir = userDir() + "/config/";
        remote().download(configDir, downloadDir, true, pattern(type));
        log.info("all config files downloaded");
        return new MyFile(downloadDir).getChildren();
    }

    public boolean nameIs(String name) {
        return this.name.equals(name);
    }

    public String user() {
        return user.getName();
    }

    public String password() {
        return user.getPassword();
    }

    private String userDir() {
        return System.getProperty("user.dir");
    }

    private MyRemote remote() {
        return new MyJsch(this);
    }

    private Pattern pattern(String type) {
        if (StringUtils.isBlank(type)) return Pattern.compile(".*");
        return Pattern.compile(type.replaceAll(",", "|"));
    }
}
                                                                              