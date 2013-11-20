package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.domain.Context;
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
        int c = 1;

        for (int i = 0; i < processedLines.size(); i++) {
            String processedLine = processedLines.get(i);
            if (logs.hasNoSearchKey(processedLine))
                continue;

            Context context = new Context("c"+c,3);
            for (int k = i - context.size(); k != i && k > 0; k++) {
                String contextLine = processedLines.get(k);
                context.add(new Line(contextLine).withTime(allLogRegex.findTimeFor(contextLine)));
            }
            context.add(new Line(processedLine).withTime(allLogRegex.findTimeFor(processedLine)));
            for (int j = i + context.size(); j != i && j < processedLines.size(); j--) {
                String contextLine = processedLines.get(j);
                context.add(new Line(contextLine).withTime(allLogRegex.findTimeFor(contextLine)));
            }
            lines.addKeyContext(context.complete());
            c++;
        }
    }


}
