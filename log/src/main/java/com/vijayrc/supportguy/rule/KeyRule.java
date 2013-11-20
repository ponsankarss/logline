package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.repository.AllLogRegex;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j
public class KeyRule implements LineRule {

    @Autowired
    private AllLogRegex allLogRegex;

    @Override
    public void process(Logs logs) {
        Lines lines = logs.lines();
        List<String> processedLines = lines.processedLines();

        int context = 3;
        int contextIndex = 1;

        for (int i = 0; i < processedLines.size(); i++) {
            String processedLine = processedLines.get(i);
            if (logs.hasNoSearchKey(processedLine))
                continue;
            for (int k = i - context; k != i && k > 0; k++) {
                String contextLine = processedLines.get(k);
                Line keyLine = new Line(contextLine).withThread("c-" + contextIndex).withTime(allLogRegex.findTimeFor(contextLine));
                lines.addKeyLine(keyLine);
            }
            lines.addKeyLine(new Line(processedLine).withThread("c-" + contextIndex));
            for (int j = i + context; j != i && j < processedLines.size(); j--) {
                String contextLine = processedLines.get(j);
                Line keyLine = new Line(contextLine).withThread("c-" + contextIndex).withTime(allLogRegex.findTimeFor(contextLine));
                lines.addKeyLine(keyLine);
            }
            contextIndex++;
        }
    }


}
