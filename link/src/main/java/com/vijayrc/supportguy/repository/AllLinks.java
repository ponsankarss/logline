package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.util.Util;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
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
public class AllLinks {

    private List<Link> links = new ArrayList<>();

    public AllLinks() throws Exception {
        String file = Util.resource("links.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            Link link = reader.read(Link.class);
            if (link == null) break;
            links.add(link);
            log.info(link);
        }
    }

    public Link getFor(String name) {
        List<Link> filter = filter(having(on(Link.class).getName(), equalTo(name)), links);
        return isNotEmpty(filter) ? filter.get(0) : null;
    }
}