package com.vrc.logline.processor;

import com.vrc.logline.domain.Config;
import com.vrc.logline.repository.AllLines;
import sun.awt.ConstrainableGraphics;

import java.util.regex.Pattern;

public class ExceptionProcessor implements Processor {
    private Config config = Config.get();
    private Pattern startPattern = Pattern.compile("(?i)exception");
    private Pattern pattern = Pattern.compile("\\s*at\\s+");
    private Pattern invalidPattern;

    public ExceptionProcessor() {
        invalidPattern = Pattern.compile("b2config.ConfigReader" +
                "|ejb.ExceptionProcessorStartUpBeanBean" +
                "|BatchResendConfigurationReader " +
                "|exception.processor.ejb.ExceptionProcessorBean" +
                "|dao.util.ReportQueryReader" +
                "|Claim type cannot be determined for SCCF" +
                "|There is no data to display for this tab" +
                "|The reply to Message ID"+
                "|Message [\\w]{32} is not found."+
                "|Authentication error during authentication for user"+
                "|Authentication failed when using LTPA"+
                "|Destination Plan Code is required"+
                "|Invalid SCCF. It must be numeric"+
                "|com.bcbsa.blue2.common.Blue2Exception: No matching records found"+
                "|com.bcbsa.blue2.common.Blue2Exception: Comments is required"+
                "|com.bcbsa.blue2.common.Blue2Exception: Invalid Action Code"+
                "|Postal Code is invalid"+
                "|Attempt Dates is required"+
                "|Phone is required"+
                "|No matching records found|(:?<.+>)"
        );
    }

    @Override
    public void process(AllLines allLines) {
        StringBuffer errorString = null;
        boolean inStack = false;

        for (String fileLine : allLines.fileLines()) {
            if (startPattern.matcher(fileLine).find() && !inStack && !invalidPattern.matcher(fileLine).find()) {
                errorString = new StringBuffer().append(fileLine);
                inStack = true;
                continue;
            }
            if (inStack) {
                if (pattern.matcher(fileLine).find()
                        || !(config.datePattern1().matcher(fileLine).find() || config.datePattern2().matcher(fileLine).find())) {
                    errorString.append(fileLine + "\n");
                } else {
                    if(invalidPattern.matcher(errorString.toString()).find()) continue;
                    allLines.addProcessedLine("[ERROR]" + errorString.toString() + "[/ERROR]");
                    inStack = false;
                }
            }
        }
    }

}
