package com.vijayrc.supportguy.service;

import ch.lambdaj.group.Group;
import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.domain.LinkHit;
import com.vijayrc.supportguy.repository.AllLinkWorkers;
import com.vijayrc.supportguy.repository.AllLinks;
import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public Group<Link> getAll() {
        return allLinks.groupByEnv();
    }
}
