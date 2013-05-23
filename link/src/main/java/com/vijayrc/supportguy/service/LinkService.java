package com.vijayrc.supportguy.service;

import ch.lambdaj.group.Group;
import com.vijayrc.supportguy.domain.Link;
import com.vijayrc.supportguy.domain.LinkHit;
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

    public LinkHit process(Link link) throws Exception {
        NameValuePair[] nameValuePairs = nameValuePairsFor(link);
        HttpMethod httpMethod = link.isGet() ? getMethodFor(link, nameValuePairs) : postMethodFor(link, nameValuePairs);

        try {
            int statusCode = new HttpClient().executeMethod(httpMethod);
            return new LinkHit(link.getFullName(), statusCode, httpMethod.getResponseBodyAsString());
        } catch (IOException e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            return new LinkHit(link.getFullName(), 0, e.getMessage());
        }
    }

    private NameValuePair[] nameValuePairsFor(Link link) {
        if (!link.hasParams()) return new NameValuePair[0];
        NameValuePair[] nameValuePairs = new NameValuePair[link.getParams().size()];
        Map<String, String> paramsMap = link.getParamsMap();
        int i = 0;
        for (String paramKey : paramsMap.keySet()) {
            nameValuePairs[i] = new NameValuePair(paramKey, paramsMap.get(paramKey));
            ++i;
        }
        return nameValuePairs;
    }

    private HttpMethod postMethodFor(Link link, NameValuePair[] nameValuePairs) {
        PostMethod postMethod = new PostMethod(link.getUrl());
        postMethod.addParameters(nameValuePairs);
        return postMethod;
    }

    private HttpMethod getMethodFor(Link link, NameValuePair[] nameValuePairs) {
        GetMethod getMethod = new GetMethod(link.getUrl());
        getMethod.setQueryString(nameValuePairs);
        return getMethod;
    }

    public Link getFor(String linkName, String environment) {
        return allLinks.getFor(linkName, environment);
    }

    public Group<Link> getAll() {
        return allLinks.groupByEnv();
    }
}
