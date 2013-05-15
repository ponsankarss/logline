package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.MyRegex;
import com.vijayrc.supportguy.util.Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
public class AllLogRegex {

    private List<MyRegex> regexes = new ArrayList<MyRegex>();

    public AllLogRegex() throws Exception {
        String file = Util.resource("logs.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            MyRegex myRegex = reader.read(MyRegex.class);
            if (myRegex == null) break;
            regexes.add(myRegex);
        }
    }

}
