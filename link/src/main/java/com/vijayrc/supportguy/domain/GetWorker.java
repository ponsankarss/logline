package com.vijayrc.supportguy.domain;

import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class GetWorker implements LinkWorker {

    @Override
    public LinkHit process(Link link) throws Exception {
        if (link.isNotGet())
            return LinkHit.noAction();

        GetMethod getMethod = new GetMethod(link.getUrl());
        getMethod.setQueryString(link.nameValuePairs());
        int statusCode = new HttpClient().executeMethod(getMethod);

        return new LinkHit(link.getFullName(), statusCode, getMethod.getResponseBodyAsString());
    }
}
