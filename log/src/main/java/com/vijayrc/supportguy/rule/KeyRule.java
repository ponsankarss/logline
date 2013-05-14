package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.config.Constants;
import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j
public class KeyRule implements LineRule {

    //TODO
    @Override
    public void process(Logs logs) {
        Lines lines = logs.lines();
        Pattern keyPattern = logs.keyPattern();
        List<String> processedLines = lines.processedLines();

        for (int i = 0; i < processedLines.size(); i++) {
            String processedLine = processedLines.get(i);
            if (!keyPattern.matcher(processedLine).find())
                continue;
            Matcher matcher = Constants.dateRegex1.matcher(processedLine);
            if (matcher != null && matcher.find()) {
                String thread = matcher.group("thread");
                lines.addKeyLine(new Line(processedLine).ofThread(thread));
                for (int j = i + Constants.context; j != i && j < processedLines.size(); j--)
                    if (processedLines.get(j).contains(thread))
                        lines.addKeyLine(new Line(processedLines.get(j)).ofThread(thread));
                for (int k = i - Constants.context; k != i && k > 0; k++)
                    if (processedLines.get(k).contains(thread))
                        lines.addKeyLine(new Line(processedLines.get(k)).ofThread(thread));
            } else {
                lines.addKeyLine(new Line(processedLine));
                for (int j = i + Constants.context; j != i && j < processedLines.size(); j--)
                    if (!Constants.dateRegex1.matcher(processedLines.get(j)).find())
                        lines.addKeyLine(new Line(processedLines.get(j)));
                for (int k = i - Constants.context; k != i && k > 0; k++)
                    if (!Constants.dateRegex1.matcher(processedLines.get(k)).find())
                        lines.addKeyLine(new Line(processedLines.get(k)));
            }
        }
    }


}
