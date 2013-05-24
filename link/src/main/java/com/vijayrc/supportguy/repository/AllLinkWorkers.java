package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.*;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
@Log4j
public class AllLinkWorkers {

    private List<LinkWorker> workers;

    @Autowired
    public AllLinkWorkers(PostWorker postWorker, GetWorker getWorker, SSLWorker sslWorker) {
        workers = new ArrayList<>();
        workers.add(sslWorker);
        workers.add(getWorker);
        workers.add(postWorker);
    }
    
    public LinkHit process(Link link){
        LinkHit linkHit = null;
        try {
            for (LinkWorker worker : workers) {
                linkHit = worker.process(link);
                if(linkHit.isProcessed()) return linkHit;
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            linkHit = new LinkHit(link.getFullName(), 0, e.getMessage()); 
        }
        return linkHit;
    }
}
