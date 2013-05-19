package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.repository.AllLogRegex;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Scope("singleton")
@Log4j
public class ErrorProcessor implements Processor {
    private AllLogRegex allLogRegex;
    private Pattern startPattern;
    private Pattern tracePattern;

    @Autowired
    public ErrorProcessor(AllLogRegex allLogRegex) {
        this.allLogRegex = allLogRegex;
        startPattern = Pattern.compile("(?i)exception");
        tracePattern = Pattern.compile("\\s*at\\s+");
    }

    @Override
    public void process(Logs logs) {
        StringBuffer buffer = null;
        boolean inStack = false;

        for (String fileLine : logs.lines().fileLines()) {
            if (startPattern.matcher(fileLine).find() && !inStack) {
                buffer = new StringBuffer().append(fileLine);
                inStack = true;
                continue;
            }
            if (inStack) {
                if (tracePattern.matcher(fileLine).find() || hasNoTimePrefix(fileLine)) {
                    buffer.append(fileLine).append("\n");
                } else {
                    logs.lines().addProcessedLine("[ERROR]" + buffer.toString() + "[/ERROR]");
                    inStack = false;
                }
            }
        }
    }
    private boolean hasNoTimePrefix(String fileLine) {
        return !allLogRegex.findMatched(fileLine).hasGroup("timestamp");
    }
}
