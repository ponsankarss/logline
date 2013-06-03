package com.vijayrc.supportguy.remote;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.User;
import org.junit.Test;

public class MyExecJschTest {

    private MyExecJsch execJsch;

    @Test
    public void shouldRunACommandInARemoteMachine() throws Exception {
        User user = new User();
        user.setName("vichakra");
        user.setPassword("tindrum210");

        Machine machine = new Machine();
        machine.setUser(user);
        machine.setIp("nasnmasdev");

        execJsch = new MyExecJsch(machine);

        execJsch.connect().tail("/was7blue2/logs/SystemOut.log");
    }
}
