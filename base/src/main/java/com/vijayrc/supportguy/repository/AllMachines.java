package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Machine;

import java.util.ArrayList;
import java.util.List;

public class AllMachines {

    private List<Machine> machines = new ArrayList<Machine>();
//    private Config config = Config.get();

    public AllMachines() {
//        machines.add(new Machine("nasnmasdev").withShortName("dev").withLogDir(config.devLogDir()).withConfigDir(config.devConfigDir()));
//        machines.add(new Machine("nasnmasmo").withShortName("mo").withLogDir(config.moLogDir()).withConfigDir(config.moConfigDir()));
//        machines.add(new Machine("nasnmas3").withShortName("prod").withLogDir(config.prodLogDir()).withConfigDir(config.prodConfigDir()));
    }

    public Machine getFor(String name) {
        for (Machine machine : machines)
            if (machine.nameIs(name))
                return machine;
        return null;
    }
}
