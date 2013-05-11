package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.config.Constants;
import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.domain.Lines;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class XMLProcessor implements Processor {

    private Pattern tagPattern = Pattern.compile("(:?<.+>)");
    private Pattern startPattern = Pattern.compile("<\\?xml\\s+");
    private Pattern invalidPattern = Pattern.compile("[><]{2,5}\\s*Start|End");

    private String xmlStart1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private String xmlStart2 = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>";

    @Override
    public void process(Logs logs) {
        Lines lines = logs.lines();
        StringBuffer buffer = new StringBuffer();
        boolean inXml = false;

        for (String fileLine : lines.fileLines()) {
            if (startPattern.matcher(fileLine).find()) {
                buffer.append(fileLine);
                inXml = true;
                continue;
            }
            if (inXml) {
                if (tagPattern.matcher(fileLine).find() || hasNoTimePrefix(fileLine) && isValid(fileLine)) {
                    buffer.append(fileLine + "\n");
                } else {
                    String xml = buffer.toString().replace(xmlStart1, "[XML]").replace(xmlStart2, "[XML]") + "[/XML]";
                    lines.addProcessedLine(xml);
                    inXml = false;
                }
            } else {
                lines.addProcessedLine(fileLine);
            }
        }
    }

    private boolean isValid(String fileLine) {
        return !invalidPattern.matcher(fileLine).find();
    }

    //TODO
    private boolean hasNoTimePrefix(String fileLine) {
        return !(Constants.dateRegex1.matcher(fileLine).find() || Constants.dateRegex2.matcher(fileLine).find());
    }

}
