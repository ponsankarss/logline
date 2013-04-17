package com.vrc.logline.processor;

import com.vrc.logline.domain.Config;
import com.vrc.logline.repository.AllLines;
import org.apache.commons.lang.StringUtils;
import sun.awt.ConstrainableGraphics;

import java.util.regex.Pattern;

public class ExceptionProcessor implements Processor {
    private Config config = Config.get();
    private Pattern startPattern = Pattern.compile("(?i)exception");
    private Pattern pattern = Pattern.compile("\\s*at\\s+");

    public ExceptionProcessor() {
    }

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
