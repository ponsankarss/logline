package com.vijayrc.supportguy.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyJsch implements MyRemote {
    private static final Logger log = Logger.getLogger(MyJsch.class);

    private String machine;
    private JSch jsch;
    private ChannelSftp channelSftp;
    private Session session;

    public MyJsch(String machine) {
        this.machine = machine;
        this.jsch = new JSch();
    }

    @Override
    public MyRemote connect() throws Exception {
        log.info("connecting to " + machine + " as ");
        session = jsch.getSession("username", machine, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword("password");
        session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
        session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
        session.setConfig("compression_level", "9");
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        log.info("connected to " + machine + " as ");
        return this;
    }

    @Override
    public MyRemote disconnect() throws Exception {
        if (channelSftp != null) channelSftp.exit();
        if (session != null) session.disconnect();
        log.info("disconnected from " + machine);
        return this;
    }

    @Override
    public List<String> browse(String sourcePath, Pattern pattern) throws Exception {
        connect();
        List<String> files = new ArrayList<String>();
        Vector vector = channelSftp.ls(sourcePath);
        for (Object o : vector) {
            ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) o;
            if (!pattern.matcher(lsEntry.getFilename()).find()) continue;
            files.add(lsEntry.getFilename() + "[" + lsEntry.getAttrs().getMtimeString() + "]");
        }
        disconnect();
        log.info("browsed [" + sourcePath + "] to give " + files.size() + " files");
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
        Vector vector = channelSftp.ls(sourcePath);
        for (Object o : vector) {
            ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) o;
            String fileName = lsEntry.getFilename();
            try {
                if (!pattern.matcher(fileName).find()) {
                    continue;
                } else if (lsEntry.getAttrs().isDir() && recurse) {
                    String sourceDir = sourcePath + "/" + fileName;
                    String targetDir = targetPath + "/" + fileName;
                    downloadRecurse(sourceDir, targetDir, recurse, pattern);
                } else {
                    String targetFile = targetPath + "\\" + fileName;
                    String sourceFile = sourcePath + "/" + fileName;
                    log.info("downloading [" + sourceFile + "]");
                    channelSftp.get(sourceFile, targetFile);
                    log.info("downloaded [" + targetFile + "]");
                }
            } catch (Exception e) {
                log.error("Error with fetching file: " + fileName, e);
            }
        }
    }
}
