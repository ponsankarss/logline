package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;

@Repository
@Scope("singleton")
@Log4j
public class AllMachines {
    private List<Machine> machines = new ArrayList<Machine>();

    public AllMachines() throws Exception {
        String file = Util.resource("machines.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            Machine machine = reader.read(Machine.class);
            if (machine == null) break;
            machines.add(machine);
        }
    }

    public AllMachines add(Machine machine) {
        machines.add(machine);
        return this;
    }

    public Machine getFor(String name) {
        List<Machine> list = filter(having(on(Machine.class).getName(), equalTo(name)), machines);
        return isNotEmpty(list) ? list.get(0) : null;
    }

    public List<String> names() {
        return extract(machines, on(Machine.class).getName());
    }
}
