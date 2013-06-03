package com.vijayrc.supportguy.remote;

import com.jcraft.jsch.*;
import com.vijayrc.supportguy.domain.ExitStatus;
import com.vijayrc.supportguy.domain.Machine;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Log4j
public class MyExecJsch {
    private JSch jsch;
    private ChannelExec channelExec;
    private Session session;
    private Machine machine;

    public MyExecJsch(Machine machine) {
        this.machine = machine;
        this.jsch = new JSch();
    }

    public MyExecJsch connect() throws Exception {
        log.info("connecting to " + machine);
        session = jsch.getSession(machine.user(), machine.getIp(), 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(machine.password());
        session.connect();

        channelExec = (ChannelExec) session.openChannel("exec");
        log.info("connected to " + machine);
        return this;
    }

    public void tail(String file) throws Exception {
        InputStream stream = channelExec.getInputStream();
        channelExec.setCommand("tail -f " + file);
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
           log.info(line);
        }
        int exitCode = channelExec.getExitStatus();
        log.info(ExitStatus.getFor(exitCode).message());
    }

    public MyExecJsch disconnect() throws Exception {
        if (channelExec != null) channelExec.disconnect();
        if (session != null) session.disconnect();
        log.info("disconnected from " + machine);
        return this;
    }


}
