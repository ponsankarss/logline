package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.remote.MyFile;
import com.vijayrc.supportguy.remote.MyFtpJsch;
import com.vijayrc.supportguy.remote.MyFtpRemote;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.vijayrc.supportguy.util.Util.fileSeparator;
import static com.vijayrc.supportguy.util.Util.userDir;

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
        String timestamp = new SimpleDateFormat("YYYYMMdd-HH-mm-ss").format(new Date());
        String downloadDir = userDir() + "logs"+fileSeparator()+name+"-"+timestamp;
        remote().download(logDir, downloadDir, false, pattern(type));
        log.info("all log files downloaded");
        return new MyFile(downloadDir).getChildren();
    }

    public List<String> getConfigFiles() throws Exception {
        String downloadDir = userDir() + "diffs";
        remote().download(configDir, downloadDir, true, null);
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

    private MyFtpRemote remote() {
        return new MyFtpJsch(this);
    }

    private Pattern pattern(String type) {
        if (StringUtils.isBlank(type)) return Pattern.compile(".*");
        return Pattern.compile(type.replaceAll(",", "|"));
    }
}
                                                                              