package com.vijayrc.supportguy.rule;

import com.vrc.logline.domain.Config;
import com.vrc.logline.domain.Line;
import com.vijayrc.supportguy.repository.AllLines;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyRule extends BaseRule {

    private Pattern threadPattern;
    private Pattern keyPattern;
    private Config config;

    public KeyRule(List<String> keys) {
        config = Config.get();
        threadPattern = config.datePattern1();
        keyPattern = Pattern.compile("(?i)" + StringUtils.join(keys.toArray(), "|"));
    }

    @Override
    protected String name() {
        return "KeyRule";
    }

    @Override
    public void process(AllLines allLines) {

        List<String> processedLines = allLines.processedLines();
        for (int i = 0; i < processedLines.size(); i++) {
            String processedLine = processedLines.get(i);
            if (!keyPattern.matcher(processedLine).find()) continue;
            Matcher matcher = threadPattern.matcher(processedLine);

            if (matcher != null && matcher.find()) {
                String thread = matcher.group("thread");
                allLines.addKeyLine(new Line(processedLine).ofThread(thread));
                for (int j = i + config.context(); j != i && j < processedLines.size(); j--) {
                    if (processedLines.get(j).contains(thread))
                        allLines.addKeyLine(new Line(processedLines.get(j)).ofThread(thread));
                }
                for (int k = i - config.context(); k != i && k > 0; k++)
                    if (processedLines.get(k).contains(thread))
                        allLines.addKeyLine(new Line(processedLines.get(k)).ofThread(thread));
            } else {
                allLines.addKeyLine(new Line(processedLine));
                for (int j = i + config.context(); j != i && j < processedLines.size(); j--)
                    if (!threadPattern.matcher(processedLines.get(j)).find())
                        allLines.addKeyLine(new Line(processedLines.get(j)));
                for (int k = i - config.context(); k != i && k > 0; k++)
                    if (!threadPattern.matcher(processedLines.get(k)).find())
                        allLines.addKeyLine(new Line(processedLines.get(k)));
            }
        }
    }
}
