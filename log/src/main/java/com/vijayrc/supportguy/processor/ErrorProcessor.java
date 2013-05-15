package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.util.Constants;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Log4j
public class ErrorProcessor implements Processor {
    private Pattern startPattern;
    private Pattern tracePattern;

    public ErrorProcessor() {
        startPattern = Pattern.compile("(?i)exception");
        tracePattern = Pattern.compile("\\s*at\\s+");
    }

    @Override
    public void process(Logs logs) {
        StringBuffer buffer = new StringBuffer();
        boolean inStack = false;

        for (String fileLine : logs.lines().fileLines()) {
            if (startPattern.matcher(fileLine).find() && !inStack) {
                buffer.append(fileLine);
                inStack = true;
                continue;
            }
            if (inStack) {
                if (tracePattern.matcher(fileLine).find() || hasNoTime(fileLine)) {
                    buffer.append(fileLine).append("\n");
                } else {
                    logs.lines().addProcessedLine("[ERROR]" + buffer.toString() + "[/ERROR]");
                    inStack = false;
                }
            }
        }
    }

    //TODO
    private boolean hasNoTime(String fileLine) {
        return !(Constants.dateRegex1.matcher(fileLine).find()
                || Constants.dateRegex2.matcher(fileLine).find());
    }
}
