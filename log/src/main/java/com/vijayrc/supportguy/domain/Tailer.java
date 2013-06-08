package com.vijayrc.supportguy.domain;

import com.vijayrc.supportguy.remote.MyExecJsch;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;

@Log4j
public class Tailer implements Runnable {
    private MyExecJsch execJsch;
    private String file;
    private ExecBuffer execBuffer;
    private String name;

    public Tailer(Machine machine, String file, int size) {
        this.file = machine.getLogDir() + "/" + file;
        this.name = machine.getName() + "-" + file;
        this.execBuffer = new ExecBuffer(size);
        this.execJsch = new MyExecJsch(machine);
    }

    @Override
    public void run() {
        try {
            log.info("started tailing " + name());
            execJsch.connect().execute("tail -f " + file, execBuffer);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    public void stop() throws Exception {
        execJsch.stop().disconnect();
        log.info("stopped tailing " + name());
    }

    public String pop() {
        return execBuffer.pop();
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tailer tailer = (Tailer) o;
        if (name != null ? !name.equals(tailer.name) : tailer.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
