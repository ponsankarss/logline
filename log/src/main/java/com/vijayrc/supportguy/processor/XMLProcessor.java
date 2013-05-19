package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.domain.MyRegex;
import com.vijayrc.supportguy.repository.AllLogRegex;
import com.vijayrc.supportguy.domain.Logs;
import com.vijayrc.supportguy.domain.Lines;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j
public class XMLProcessor implements Processor {

    private AllLogRegex allLogRegex;
    private MyRegex tagRegex;
    private MyRegex startRegex;
    private MyRegex invalidRegex;

    @Autowired
    public XMLProcessor(AllLogRegex allLogRegex) {
        this.allLogRegex = allLogRegex;
        this.tagRegex = new MyRegex("(:?<.+>)").compile();
        this.startRegex = new MyRegex("<\\?xml\\s+").compile();
        this.invalidRegex = new MyRegex("[><]{2,5}\\s*Start|End").compile();
    }

    @Override
    public void process(Logs logs) {
        Lines lines = logs.lines();
        StringBuffer buffer = null;
        boolean inXml = false;

        for (String fileLine : lines.fileLines()) {
            if (startRegex.on(fileLine).isMatch()) {
                buffer = new StringBuffer().append(fileLine);
                inXml = true;
                continue;
            }
            if (inXml) {
                if (tagRegex.on(fileLine).isMatch() || hasNoTimePrefix(fileLine) && invalidRegex.on(fileLine).isMatch()) {
                    buffer.append(fileLine + "\n");
                } else {
                    String xml = buffer.toString()
                            .replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "[XML]")
                            .replace("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>", "[XML]")
                            + "[/XML]";
                    lines.addProcessedLine(xml);
                    inXml = false;
                }
            } else {
                lines.addProcessedLine(fileLine);
            }
        }
    }

    private boolean hasNoTimePrefix(String fileLine) {
        return !allLogRegex.findMatched(fileLine).hasGroup("timestamp");
    }

}
