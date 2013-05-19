package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Scm;
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
public class AllScms {

    private List<Scm> scms = new ArrayList<>();

    public AllScms() throws Exception {
        String file = Util.resource("scm.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            Scm scm = reader.read(Scm.class);
            if (scm == null) break;
            scms.add(scm);
        }
    }

    public Scm getFor(String name) {
        List<Scm> filter = filter(having(on(Scm.class).getName(), equalTo(name)), scms);
        return isNotEmpty(filter) ? filter.get(0) : null;
    }

    public List<String> names() {
        return extract(scms,on(Scm.class).getName());
    }
}
