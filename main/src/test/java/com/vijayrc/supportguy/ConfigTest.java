package com.vijayrc.supportguy;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Machine;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigTest {

    @Test
    public void shouldReadYamlFileAndLoadObjectGraphs() throws FileNotFoundException, YamlException {
        String file = ClassLoader.getSystemResource("machines.yml").getFile();
        System.out.println(file);
        YamlReader reader = new YamlReader(new FileReader(file));
        Machine machine = reader.read(Machine.class);
        System.out.println(machine.name());

    }
}
