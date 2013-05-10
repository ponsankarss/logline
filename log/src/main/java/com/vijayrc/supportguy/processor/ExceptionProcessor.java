package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.repository.AllLines;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ExceptionProcessor implements Processor {
    private Pattern startPattern = Pattern.compile("(?i)exception");
    private Pattern pattern = Pattern.compile("\\s*at\\s+");

    @Override
    public void process(AllLines allLines) {
        StringBuffer errorString = null;
        boolean inStack = false;
        for (String fileLine : allLines.fileLines()) {
            if (startPattern.matcher(fileLine).find() && !inStack) {
                errorString = new StringBuffer().append(fileLine);
                inStack = true;
                continue;
            }
            if (inStack) {
                if (pattern.matcher(fileLine).find()
                        || !(config.datePattern1().matcher(fileLine).find()
                        || config.datePattern2().matcher(fileLine).find())) {
                    errorString.append(fileLine + "\n");
                } else {
                    allLines.addProcessedLine("[ERROR]" + errorString.toString() + "[/ERROR]");
                    inStack = false;
                }
            }
        }
    }

}
