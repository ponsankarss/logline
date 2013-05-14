package com.vijayrc.supportguy.remote;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Log4j
public class MyFtp implements MyRemote {
    private String machine;
    private FTPClient ftpClient;

    public MyFtp(String machine) {
        this.machine = machine;
    }

    @Override
    public MyRemote connect() throws Exception {
        log.info("connecting to " + machine);
        ftpClient = new FTPClient();
        ftpClient.connect(machine);
        ftpClient.login("username", "password");
        log.info("connected to " + machine);
        return this;
    }

    @Override
    public MyRemote disconnect() throws Exception {
        if (ftpClient != null) ftpClient.disconnect();
        log.info("disconnected from " + machine);
        return this;
    }

    @Override
    public List<String> browse(String sourcePath, Pattern pattern) throws Exception {
        connect();
        List<String> files = new ArrayList<String>();
        for (FTPFile ftpFile : ftpClient.listFiles(sourcePath)) {
            if (!pattern.matcher(ftpFile.getName()).find()) continue;
            files.add(ftpFile.getName() + "   [" + ftpFile.getTimestamp().getTime() + "]");
        }
        log.info("browsed [" + sourcePath + "] to give " + files.size() + " files");
        disconnect();
        return files;
    }

    @Override
    public void download(String sourcePath, String targetPath, Boolean recurse, Pattern pattern) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        connect();
        downloadRecurse(sourcePath, targetPath, recurse, pattern);
        disconnect();
        watch.stop();
        log.info("time taken to download: " + watch);
    }

    private void downloadRecurse(String sourcePath, String targetPath, Boolean recurse, Pattern pattern) throws Exception {
        new MyFile(targetPath).recreate();
        for (FTPFile ftpFile : ftpClient.listFiles(sourcePath)) {
            String fileName = ftpFile.getName();
            try {
                if (!pattern.matcher(fileName).find()) {
                    continue;
                } else if (ftpFile.isDirectory() && recurse) {
                    String sourceDir = sourcePath + "/" + ftpFile.getName();
                    String targetDir = targetPath + "/" + ftpFile.getName();
                    downloadRecurse(sourceDir, targetDir, recurse, pattern);
                } else {
                    String targetFile = targetPath + "\\" + fileName;
                    String sourceFile = sourcePath + "/" + fileName;
                    log.info("downloading [" + sourceFile + "]");
                    FileOutputStream targetStream = new FileOutputStream(new File(targetFile));
                    ftpClient.retrieveFile(sourceFile, targetStream);
                    targetStream.close();
                    log.info("downloaded [" + targetFile + "]");
                }
            } catch (Exception e) {
                log.error("error with fetching file: " + fileName, e);
            }
        }
    }


}
