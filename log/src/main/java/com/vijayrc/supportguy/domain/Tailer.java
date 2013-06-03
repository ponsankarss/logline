package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.remote.MyExecJsch;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;

@Log4j
public class Tailer implements Runnable {
    private MyExecJsch execJsch;
    private String file;

    public Tailer(Machine machine, String file){
        this.file = file;
        this.execJsch = new MyExecJsch(machine);
    }

    @Override
    public void run() {
        try {
            execJsch.connect().execute("tail -f "+file);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    public void stop() throws Exception {
        execJsch.stop().disconnect();
    }
}
