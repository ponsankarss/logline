package com.vrc.logline.rule;

import com.vrc.logline.domain.Line;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.regex.Pattern;

public class ThreadRule extends BaseRule {

    private Pattern xmlStartPattern = Pattern.compile("<\\?xml\\s+");

    @Override
    public void apply(String inputLine, RulePackage rulePackage, Set<Line> outputLines, String file) {
        for (String thread : rulePackage.threads())
            if (inputLine.contains(thread))
                outputLines.add(new Line(inputLine).ofThread(thread).ofFile(file));
    }

    @Override
    protected String name() {
        return "ThreadRule";
    }
}