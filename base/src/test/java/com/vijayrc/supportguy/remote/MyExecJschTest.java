package com.vijayrc.supportguy.remote;

import com.vijayrc.supportguy.domain.ExecBuffer;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

@Log4j
public class MyExecJschTest {

    private MyExecJsch execJsch;

    @Test
    public void shouldRunACommandInARemoteMachine() throws Exception {
        User user = new User();
        user.setName("vichakra");
        user.setPassword("xxx");

        Machine machine = new Machine();
        machine.setUser(user);
        machine.setIp("nasnmasdev");

        ExecBuffer execQueue = new ExecBuffer(30);

        execJsch = new MyExecJsch(machine);
        execJsch.connect().execute("tail -f /was7blue2/logs/SystemOut.log", execQueue);
    }
}
