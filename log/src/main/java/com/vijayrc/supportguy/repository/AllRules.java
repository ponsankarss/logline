package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.rule.ErrorRule;
import com.vijayrc.supportguy.rule.KeyRule;
import com.vijayrc.supportguy.rule.LineRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AllRules {
    @Autowired
    private KeyRule keyRule;
    @Autowired
    private ErrorRule errorRule;

    public void process(Logs logs) throws Exception {
        List<LineRule> rules = new ArrayList<LineRule>();
        String option = logs.option();

        if (option.equals("error"))
            rules.add(errorRule);
        else if (option.equals("key"))
            rules.add(keyRule);
        else {
            rules.add(keyRule);
            rules.add(errorRule);
        }
        for (LineRule rule : rules)
            rule.process(logs);
    }
}
