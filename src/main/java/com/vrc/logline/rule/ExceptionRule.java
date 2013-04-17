package com.vrc.logline.rule;

import com.vrc.logline.domain.Config;
import com.vrc.logline.domain.Line;
import com.vrc.logline.repository.AllLines;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionRule extends BaseRule {

    private Config config = Config.get();

    @Override
    protected String name() {
        return "ExceptionRule";
    }

    @Override
    public void process(AllLines allLines) throws Exception {
        for (String processedLine : allLines.processedLines()) {
            if (!processedLine.contains("[/ERROR]"))
                continue;
            String title = StringUtils.substringBefore(processedLine, "\n");
            if (invalidPattern.matcher(title).find())
                continue;

            String timeStamp = "XXXX-XX-XX";
            Matcher matcher = config.datePattern1().matcher(title);
            if (matcher.find()) {
                timeStamp = matcher.group("timestamp");
            } else {
                matcher = config.datePattern2().matcher(title);
                if (matcher.find())
                    timeStamp = matcher.group();
            }
            Line errorLine = new Line(processedLine).ofFile(allLines.file()).markError();
            errorLine.markTime(timeStamp);
            errorLine.markErrorTitle(title.replaceAll("\\[.*\\]", "").replaceAll(timeStamp, ""));
            allLines.addErrorLine(errorLine);
        }
    }

    private Pattern invalidPattern = Pattern.compile("b2config.ConfigReader" +
            "|ejb.ExceptionProcessorStartUpBeanBean" +
            "|BatchResendConfigurationReader " +
            "|exception.processor.ejb.ExceptionProcessorBean" +
            "|dao.util.ReportQueryReader" +
            "|Claim type cannot be determined for SCCF" +
            "|There is no data to display for this tab" +
            "|The reply to Message ID" +
            "|Message [\\w]{32} is not found." +
            "|Authentication error during authentication for user" +
            "|Authentication failed when using LTPA" +
            "|Destination Plan Code is required" +
            "|Invalid SCCF. It must be numeric" +
            "|com.bcbsa.blue2.common.Blue2Exception: No matching records found" +
            "|com.bcbsa.blue2.common.Blue2Exception: Comments is required" +
            "|com.bcbsa.blue2.common.Blue2Exception: Invalid Action Code" +
            "|Postal Code is invalid" +
            "|Attempt Dates is required" +
            "|Phone is required" +
            "|Content Name is required for Content Key"+
            "|Subscriber ID is required"+
            "|Claim search not found for SCCF"+
            "|Failed to get content for Content Key"+
            "|Message search not found for"+
            "|First Attempt Date for Medical Record Request already exists"+
            "|Email is required."+
            "|Postal Code is required"+
            "|Invalid Medical Record Request SCCF"+
            "|Claim Type is required"+
            "|Open request message not found for"+
            "|Requested Information Source is required"+
            "|Invalid SCCF. Minimum length for it is 15 character"+
            "|Receipt Date must be greater than From Date"+
            "|Reason Code is required"+
            "|No matching records found|(:?<.+>)"
    );
}

