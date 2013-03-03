package com.vrc.logline;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LogPatternTest {

    @Test
    public void shouldMatchDatePattern1AndPickThreadName() throws ParseException {
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2},[0-9]{0,3}]\\s*(?<thread>.*)\\s*INFO");
        Matcher matcher = pattern.matcher("[2013-02-04 09:57:58,574] [WebContainer : 3538] INFO  com.xxx.xxx.web.action.SFClaimSummaryAction - UI sccfNum input:2345343");
        assertTrue(matcher != null && matcher.find());
        System.out.println(matcher.group("thread"));
    }

    @Test
    public void shouldMatchDatePattern2() throws ParseException {
        Pattern pattern = Pattern.compile("[0-9]{2}\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*[0-9]{4}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher("04 Feb 2013 09:55:10  INFO JaxmRouterManager:64 - authority is matching.");
        assertTrue(matcher != null && matcher.find());
        System.out.println(matcher.group());
    }

    @Test
    public void shouldMatchLogPattern3() throws ParseException {
        String regex = "[[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}\\s*[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}:.*]\\s*.*\\s*SystemOut\\s*O";
        String sample = "[1/26/13 8:14:20:706 EST] 00010113 SystemOut     O [2013-01-26 08:14:20,706] [WebContainer : 64071] INFO  com.bcbsa.blue2.web.action.SccfSearchAction - DisplayList.size() 4".replaceAll(regex, "XX");
        System.out.println(sample);

        String result = "1104 [pool-1-thread-8] INFO  com.vrc.logline.controller.HomeController".replaceAll("(INFO|ERROR)", "XX");
        System.out.println(result);
    }

    @Test
    public void shouldMatchXMLPattern() throws IOException {
        Pattern pattern = Pattern.compile("<\\?xml\\s+");
        Matcher matcher = pattern.matcher("[2013-01-23 10:43:07,544] [WebContainer : 23606] INFO  com.bcbsa.blue2.web.action.SccfSearchAction - SDO Output <?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        assertTrue(matcher != null && matcher.find());
        System.out.println(matcher.toMatchResult().toString());

        pattern = Pattern.compile("(:?<.+>)");
        String path = this.getClass().getResource("/sample.txt").getFile();
        matcher = pattern.matcher(FileUtils.readFileToString(new File(path)));
        assertTrue(matcher != null && matcher.find());
        while(matcher.find()){
            System.out.println(StringEscapeUtils.escapeXml(matcher.group()));
        }
    }

    @Test
    public void splitString(){
        String keys = "42320130170009000,   JZW2CM";
        for (String key : StringUtils.split(keys, ",")) {
            System.out.println(StringUtils.deleteWhitespace(key));
        }
    }

    @Test
    public void shouldMatchException(){
        Pattern invalidPattern = Pattern.compile("b2config.ConfigReader" +
                "|ejb.ExceptionProcessorStartUpBeanBean" +
                "|exception.processor.ejb.ExceptionProcessorBean" +
                "|dao.util.ReportQueryReader" +
                "|Claim type cannot be determined for SCCF" +
                "|There is no data to display for this tab" +
                "|No matching records found");
        String line1 = "INFO com.bcbsa.blue2.xx.processor.common.configuration.b2config.ConfigReader - boid value: 7802[/ERROR]";
        String line2 = "INFO com.bcbsa.blue2.xx.processor.common.configuration.ejb.ExceptionProcessorStartUpBeanBean - boid value: 7802[/ERROR]";
        String line3 = "000100fe SystemOut O [2013-01-26 08:01:26,169] [MessageListenerThreadPool : 732] ERROR com.bcbsa.blue2.service.notification.ProcessInterplanMessageHandler - Blue2Exception in ProcessInterPlanMessageHandler.com.bcbsa.blue2.common.Blue2Exception: No matching records found";
        System.out.println(invalidPattern.matcher(line1).find());
        System.out.println(invalidPattern.matcher(line2).find());
        System.out.println(invalidPattern.matcher(line3).find());

        String toStrip = "[b2_node1.log.2][2013-01-23 10:43:24,606] [WebContainer : 23644] INFO com.bcbsa.blue2.service.dataservice.ClaimSummaryHandler - FindClaimDetailsInput.isRetrieveClaim = false";
        System.out.println(toStrip.replaceAll("\\[.*\\]",""));
    }

    @Test
    public void shouldMatchFilePattern(){
        Pattern pattern = Pattern.compile("home|scripts|.properties\\z|.xml\\z|.xsd|.py|.sh|blue2");
        assertFalse(pattern.matcher("vijay.properties.3456").find());
        assertTrue(pattern.matcher("vijay.properties").find());

        String fileDiffs = "[DeleteDelta, position: 375, lines: [, , ]]\n" +
                "[DeleteDelta, position: 392, lines: [, ]]\n" +
                "[DeleteDelta, position: 400, lines: [,    , ]]\n" +
                "[DeleteDelta, position: 410, lines: [, , , ]]"+
                "[DeleteDelta, position: 410, lines: [ABC]]";

        Pattern pattern2 = Pattern.compile("lines:\\s*\\[(?<change>.*?)\\]\\]");
        Matcher matcher = pattern2.matcher(fileDiffs);

        Pattern pattern3 = Pattern.compile("[^(,\\s+)]+");
        while(matcher.find()){
            String change = matcher.group("change");
            System.out.println(change+"|"+pattern3.matcher(change).find());
        }

    }

}
