package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.domain.LinkHit;
import com.vijayrc.supportguy.repository.AllLinkWorkers;
import com.vijayrc.supportguy.repository.AllLinks;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j
public class LinkService {
    @Autowired
    private AllLinks allLinks;
    @Autowired
    private AllLinkWorkers allLinkWorkers;

    public LinkHit process(Link link) throws Exception {
        return allLinkWorkers.process(link);
    }

    public Link getFor(String linkName, String environment) {
        return allLinks.getFor(linkName, environment);
    }

    public Map<String,List<Link>> getAll() {
        return allLinks.groupByEnv();
    }
}
