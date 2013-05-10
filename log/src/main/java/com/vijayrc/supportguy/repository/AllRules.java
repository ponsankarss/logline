package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.rule.BaseRule;
import com.vijayrc.supportguy.rule.ExceptionRule;
import com.vijayrc.supportguy.rule.KeyRule;
import com.vijayrc.supportguy.rule.LineRule;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AllRules {
    private static final Logger log = Logger.getLogger(AllRules.class);
    private List<BaseRule> lineRules = new ArrayList<BaseRule>();

    public AllRules(List<String> keys, String option) {
        KeyRule keyRule = new KeyRule(keys);
        ExceptionRule exceptionRule = new ExceptionRule();

        if (option.equals("error"))
            lineRules.add(exceptionRule);
        else if (option.equals("key"))
            lineRules.add(keyRule);
        else {
            lineRules.add(keyRule);
            lineRules.add(exceptionRule);
        }
    }

    public void process(AllLines allLines) throws Exception {
        for (LineRule lineRule : lineRules)
            lineRule.process(allLines);
        log.info("rules done: " + allLines.file());
    }
}
