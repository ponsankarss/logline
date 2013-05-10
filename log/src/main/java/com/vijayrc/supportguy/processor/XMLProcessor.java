package com.vijayrc.supportguy.processor;

import com.vijayrc.supportguy.repository.AllLines;

import java.util.regex.Pattern;

public class XMLProcessor implements Processor {

    private Pattern pattern = Pattern.compile("(:?<.+>)");
    private Pattern startPattern = Pattern.compile("<\\?xml\\s+");
    private Pattern invalidPattern = Pattern.compile("[><]{2,5}\\s*Start|End");
    private final String xmlStart1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private final String xmlStart2 = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>";

    @Override
    public void process(AllLines allLines) {
        StringBuffer xmlString = null;
        boolean inXml = false;

        for (String fileLine : allLines.fileLines()) {
            if (startPattern.matcher(fileLine).find()) {
                xmlString = new StringBuffer().append(fileLine);
                inXml = true;
                continue;
            }
            if (inXml) {
                if (pattern.matcher(fileLine).find()
                        || !(config.datePattern1().matcher(fileLine).find() || config.datePattern2().matcher(fileLine).find())
                        && !invalidPattern.matcher(fileLine).find()) {
                    xmlString.append(fileLine + "\n");
                } else {
                    String xml = xmlString.toString().replace(xmlStart1, "[XML]").replace(xmlStart2, "[XML]") + "[/XML]";
                    allLines.addProcessedLine(xml);
                    inXml = false;
                }
            } else {
                allLines.addProcessedLine(fileLine);
            }
        }
    }

}
