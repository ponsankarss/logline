package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.rule.ErrorRule;
import com.vijayrc.supportguy.rule.KeyRule;
import com.vijayrc.supportguy.rule.LineRule;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j
public class AllRules {
    @Autowired
    private KeyRule keyRule;
    @Autowired
    private ErrorRule errorRule;

    public void process(Logs logs) throws Exception {
        List<LineRule> rules = new ArrayList<LineRule>();
        switch (logs.option()){
            case "error":rules.add(errorRule);
            case "key":rules.add(keyRule);
            default:rules.add(errorRule);rules.add(keyRule);
        }
        for (LineRule rule : rules)
            rule.process(logs);
    }
}
