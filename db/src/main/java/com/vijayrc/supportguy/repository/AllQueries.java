package com.vijayrc.supportguy.repository;

import ch.lambdaj.group.Group;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Database;
import com.vijayrc.supportguy.domain.Query;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.group.Groups.by;
import static ch.lambdaj.group.Groups.group;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;

@Repository
@Scope("singleton")
@Log4j
public class AllQueries {
    private AllDatabases allDatabases;
    private List<Query> queries = new ArrayList<>();

    @Autowired
    public AllQueries(AllDatabases allDatabases) throws Exception {
        this.allDatabases = allDatabases;

        String file = Util.resource("sqls.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            Query query = reader.read(Query.class);
            if (query == null) break;
            query.setDatabase(allDatabases.findByName(query.getDb()));
            queries.add(query);
            log.info(query);
        }
    }

    public Query findBy(String name, String db) {
        List<Query> filteredByName = filter(having(on(Query.class).getName(), equalTo(name)), queries);
        List<Query> filteredByDb = filter(having(on(Query.class).getDb(), equalTo(db)), filteredByName);
        return isNotEmpty(filteredByDb) ? filteredByDb.get(0) : null;
    }

    public Group<Query> groupByDb(){
        return group(queries, by(on(Query.class).getDb()));
    }
}
