package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.domain.MyMatcher;
import com.vijayrc.supportguy.repository.AllLogRegex;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j
public class KeyRule implements LineRule {

    private int context = 20;

    @Autowired
    private AllLogRegex allLogRegex;

    @Override
    public void process(Logs logs) {
        Lines lines = logs.lines();
        List<String> processedLines = lines.processedLines();

        for (int i = 0; i < processedLines.size(); i++) {
            String processedLine = processedLines.get(i);
            if (logs.hasNoSearchKey(processedLine))
                continue;
            MyMatcher myMatcher = allLogRegex.findMatched(processedLine);
            if (myMatcher.notMatched())
                continue;
            String thread = myMatcher.group("thread");
            if (StringUtils.isNotBlank(thread)){
                for (int j = i + context; j != i && j < processedLines.size(); j--) {
                    String line = processedLines.get(j);
                    if (line != null && line.contains(thread))
                        lines.addKeyLine(new Line(line).ofThread(thread));
                }
                for (int k = i - context; k != i && k > 0; k++) {
                    String line = processedLines.get(k);
                    if (line != null && line.contains(thread))
                        lines.addKeyLine(new Line(line).ofThread(thread));
                }
            }else{
                for (int j = i + context; j != i && j < processedLines.size(); j--) {
                    String line = processedLines.get(j);
                    if (line != null)
                        lines.addKeyLine(new Line(line).ofThread(thread));
                }
                for (int k = i - context; k != i && k > 0; k++) {
                    String line = processedLines.get(k);
                    if (line != null)
                        lines.addKeyLine(new Line(line).ofThread(thread));
                }

            }

        }
    }


}
