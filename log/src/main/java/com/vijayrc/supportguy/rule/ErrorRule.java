package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.domain.MyMatcher;
import com.vijayrc.supportguy.repository.AllLogRegex;
import com.vijayrc.supportguy.repository.AllRejects;
import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class ErrorRule implements LineRule {

    @Autowired
    private AllLogRegex allLogRegex;
    @Autowired
    private AllRejects allRejects;

    @Override
    public void process(Logs logs) throws Exception {
        Lines lines = logs.lines();
        for (String processedLine : lines.processedLines()) {
            if (isNotError(processedLine)) continue;
            String title = getTitle(processedLine);
            if (isInValid(title)) continue;
            String timeStamp = getTime(title);

            Line errorLine = new Line(processedLine).ofFile(lines.file()).markError();
            errorLine.timeIs(timeStamp).titleIs(title.replaceAll("\\[.*\\]", "").replaceAll(timeStamp, ""));
            lines.addErrorLine(errorLine);
        }
    }

    private String getTitle(String processedLine) {
        return StringUtils.substringBefore(processedLine, "\n");
    }

    private boolean isNotError(String processedLine) {
        return !processedLine.contains("[/ERROR]");
    }

    private boolean isInValid(String title) {
        return allRejects.matches(title);
    }

    private String getTime(String title) {
        MyMatcher myMatcher = allLogRegex.findMatched(title);
        if (myMatcher.notMatched()) return "XXXX-XX-XX";
        return myMatcher.group("timestamp");
    }

}

