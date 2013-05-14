package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Machine;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
@Log4j
public class AllMachines {
    private List<Machine> machines = new ArrayList<Machine>();

    public AllMachines add(Machine machine){
        machines.add(machine);
        return this;
    }

    public Machine getFor(String name) {
        for (Machine machine : machines)
            if (machine.nameIs(name))
                return machine;
        return null;
    }
}
