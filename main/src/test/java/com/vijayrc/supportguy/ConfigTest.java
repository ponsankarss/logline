package com.vijayrc.supportguy;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Machine;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigTest {

    @Test
    public void shouldReadYamlFileAndLoadObjectGraphs() throws FileNotFoundException, YamlException {
        String file = ClassLoader.getSystemResource("machines.yml").getFile();
        YamlReader reader = new YamlReader(new FileReader(file));

        List<Machine> machines = new ArrayList<Machine>();
        while(true){

            Machine machine = reader.read(Machine.class);
            if(machine == null) break;
            machines.add(machine);
            System.out.println(machine);
        }

    }
}
