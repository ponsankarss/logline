package com.vijayrc.supportguy.domain;

import lombok.extern.log4j.Log4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class PostWorker implements LinkWorker{

    @Override
    public LinkHit process(Link link) throws Exception {
        if(link.isNotPost())
            return LinkHit.noAction();
        log.info("processing "+link);
        PostMethod postMethod = new PostMethod(link.getUrl());
        postMethod.addParameters(link.nameValuePairs());
        int statusCode = new HttpClient().executeMethod(postMethod);
        return new LinkHit(link.getFullName(), statusCode, postMethod.getResponseBodyAsString());
    }
}
