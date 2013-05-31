package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Database;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;

@Repository
@Scope("singleton")
@Log4j
public class AllDatabases {

    private List<Database> databases = new ArrayList<>();

    public AllDatabases() throws Exception {
        String file = Util.resource("dbs.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            Database database = reader.read(Database.class);
            if (database == null) break;
            databases.add(database);
            log.info(database);
        }
    }

    public Database findByName(String name){
        List<Database> filter = filter(having(on(Database.class).getName(), equalTo(name)), databases);
        return isNotEmpty(filter)?filter.get(0):null;
    }
}
