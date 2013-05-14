package com.vijayrc.supportguy;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.User;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ConfigTest {

    @Test
    public void shouldReadYamlFileAndLoadObjectGraphs() throws Exception {
        String file = ClassLoader.getSystemResource("machines.yml").getFile();
        YamlReader reader = new YamlReader(new FileReader(file));
        List<Machine> machines = new ArrayList<Machine>();
        while (true) {
            Machine machine = reader.read(Machine.class);
            if (machine == null) break;
            machines.add(machine);
        }
        assertEquals(3, machines.size());
        assertEquals("vichakra", machines.get(0).getUser().getName());
    }

    @Test
    public void shouldWriteAndReadAComplexObject() throws Exception {
        YamlWriter writer = new YamlWriter(new FileWriter("complex.yml"));
        writer.getConfig().setPropertyElementType(Machine.class, "user", User.class);
        writer.write(new Machine());
        writer.close();


    }
}
