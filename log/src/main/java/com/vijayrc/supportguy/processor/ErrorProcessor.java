package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.config.Constants;
import com.vijayrc.supportguy.domain.Logs;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ErrorProcessor implements Processor {

    private Pattern startPattern;
    private Pattern tracePattern;

    public ErrorProcessor() {
        startPattern = Pattern.compile("(?i)exception");
        tracePattern = Pattern.compile("\\s*at\\s+");
    }

    @Override
    public void process(Logs request) {
        StringBuffer buffer = new StringBuffer();
        boolean inStack = false;
        for (String fileLine : request.lines().fileLines()) {
            if (startPattern.matcher(fileLine).find() && !inStack) {
                buffer.append(fileLine);
                inStack = true;
                continue;
            }
            if (inStack) {
                if (tracePattern.matcher(fileLine).find() || isTraceLine(fileLine)) {
                    buffer.append(fileLine).append("\n");
                } else {
                    request.lines().addProcessedLine("[ERROR]" + buffer.toString() + "[/ERROR]");
                    inStack = false;
                }
            }
        }
    }

    private boolean isTraceLine(String fileLine) {
        return !(Constants.dateRegex1.matcher(fileLine).find()
                || Constants.dateRegex2.matcher(fileLine).find());
    }
}
