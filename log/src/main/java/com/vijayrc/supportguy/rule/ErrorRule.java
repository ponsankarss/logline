package com.vijayrc.supportguy.rule;

import com.vijayrc.supportguy.util.Constants;
import com.vijayrc.supportguy.domain.Line;
import com.vijayrc.supportguy.domain.Lines;
import com.vijayrc.supportguy.domain.Logs;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Log4j
public class ErrorRule implements LineRule {

    @Override
    public void process(Logs logs) throws Exception {
        Lines lines = logs.lines();
        for (String processedLine : lines.processedLines()) {
            if (isNotError(processedLine))
                continue;
            String title = getTitle(processedLine);
            if (isInValid(title))
                continue;
            String timeStamp = getTime(title);
            Line errorLine = new Line(processedLine).ofFile(lines.file()).markError();
            errorLine.markTime(timeStamp);
            errorLine.markErrorTitle(title.replaceAll("\\[.*\\]", "").replaceAll(timeStamp, ""));
            lines.addErrorLine(errorLine);
        }
    }

    private String getTitle(String processedLine) {
        return StringUtils.substringBefore(processedLine, "\n");
    }

    private boolean isNotError(String processedLine) {
        return !processedLine.contains("[/ERROR]");
    }

    private boolean isInValid(String title) {
        return invalidPattern.matcher(title).find();
    }

    //TODO
    private String getTime(String title) {
        String timeStamp = "XXXX-XX-XX";
        Matcher matcher = Constants.dateRegex1.matcher(title);
        if (matcher.find()) {
            timeStamp = matcher.group("timestamp");
        } else {
            matcher = Constants.dateRegex2.matcher(title);
            if (matcher.find())
                timeStamp = matcher.group();
        }
        return timeStamp;
    }

    //TODO
    private Pattern invalidPattern = Pattern.compile("b2config.ConfigReader" +
            "|ejb.ExceptionProcessorStartUpBeanBean" +
            "|BatchResendConfigurationReader " +
            "|exception.processor.ejb.ExceptionProcessorBean" +
            "|dao.util.ReportQueryReader" +
            "|Claim type cannot be determined for SCCF" +
            "|There is no data to display for this tab" +
            "|The reply to Message ID" +
            "|Message" +
            " [\\w]{32} is not found." +
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
            "|Content Name is required for Content Key" +
            "|Subscriber ID is required" +
            "|Claim search not found for SCCF" +
            "|Failed to get content for Content Key" +
            "|Message search not found for" +
            "|First Attempt Date for Medical Record Request already exists" +
            "|Email is required." +
            "|Postal Code is required" +
            "|Invalid Medical Record Request SCCF" +
            "|Claim Type is required" +
            "|Open request message not found for" +
            "|Requested Information Source is required" +
            "|Invalid SCCF. Minimum length for it is 15 character" +
            "|Receipt Date must be greater than From Date" +
            "|Reason Code is required" +
            "|No matching records found|(:?<.+>)"
    );
}

