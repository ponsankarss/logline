package com.vijayrc.supportguy.domain;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.io.File;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.*;

public class MyRegexTest {

    MyRegex myRegex = new MyRegex();

    @Test
    public void matchForPattern1(){
        myRegex.setRegex("(?<timestamp>[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2},[0-9]{0,3})]\\s+\\[(?<thread>.*)\\]\\s+");
        myRegex.compile();
        MyMatcher myMatcher = myRegex.on("[2013-01-23 10:43:05,906] [WebContainer : 23613] INFO  com.bcbsa.blue2.web.action.SccfSearchAction ");
        assertTrue(myMatcher.isMatch());
        System.out.println(myMatcher.group("timestamp")+"|"+myMatcher.group("thread"));
    }

    @Test
    public void matchForPattern2(){
        myRegex.setRegex("(?<timestamp>[0-9]{1,2}/[0-9]{1,2}/[0-9]{2}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2}:[0-9]{0,3})\\s*(EDT)\\]\\s+(?<thread>[\\S]+)\\s+");
        myRegex.compile();
        MyMatcher myMatcher = myRegex.on("[5/1/13 4:45:00:803 EDT] 00000035 SchedulerDaem W   SCHD0103W: The Scheduler Datanet Lite Scheduler (scheduler/dnlScheduler) was unable to run task 2788");
        assertTrue(myMatcher.isMatch());
        System.out.println(myMatcher.group("timestamp")+"|"+myMatcher.group("thread"));
    }

    @Test
    public void matchForPattern3(){
        myRegex.setRegex("(?<timestamp>[0-9]{2}\\s+(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+[0-9]{4}\\s+[0-9]{1,2}:[0-9]{2}:[0-9]{2})");
        myRegex.compile();
        MyMatcher myMatcher = myRegex.on("15 May 2013 14:33:58  INFO RemediationMDBBean:47 - Start");
        assertTrue(myMatcher.isMatch());
        System.out.println(myMatcher.group("timestamp")+"|"+myMatcher.group("thread"));
    }

    @Test
    public void shouldMatchXMLPattern() throws Exception {
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
        System.out.println(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss").parseDateTime("03/12/2013 16:30:15"));
        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS").parseDateTime("2013-02-04 09:57:58,574"));
        System.out.println(DateTimeFormat.forPattern("dd MMM yyyy HH:mm:ss").parseDateTime("02 Mar 2013 03:49:45"));
    }

    @Test
    public void shouldMatchException(){
        Pattern invalidPattern = Pattern.compile("b2config.ConfigReader" +
                "|ejb.ExceptionProcessorStartUpBeanBean" +
                "|exception.processor.ejb.ExceptionProcessorBean" +
                "|dao.util.ReportQueryReader" +
                "|Claim type cannot be determined for SCCF" +
                "|There is no data to display for this tab" +
                "|Message [\\w]{32} is not found."+
                "|No matching records found");
        String line1 = "INFO com.bcbsa.blue2.xx.processor.common.configuration.b2config.ConfigReader - boid value: 7802[/ERROR]";
        String line2 = "INFO com.bcbsa.blue2.xx.processor.common.configuration.ejb.ExceptionProcessorStartUpBeanBean - boid value: 7802[/ERROR]";
        String line3 = "000100fe SystemOut O [2013-01-26 08:01:26,169] [MessageListenerThreadPool : 732] ERROR com.bcbsa.blue2.service.notification.ProcessInterplanMessageHandler - Blue2Exception in ProcessInterPlanMessageHandler.com.bcbsa.blue2.common.Blue2Exception: No matching records found";
        String line4 = "000100fe SystemOut O [2013-01-26 08:01:26,169] Claim type cannot be determined for SCCF (16120130380013101).";
        String line5 = "Message 0b400165e4d96766f9bc3953d425f8e7 is not found.";

        System.out.println(invalidPattern.matcher(line1).find());
        System.out.println(invalidPattern.matcher(line2).find());
        System.out.println(invalidPattern.matcher(line3).find());
        System.out.println(invalidPattern.matcher(line4).find());
        System.out.println(invalidPattern.matcher(line5).find());

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

        System.out.println("node1.log   [2013-01-23 10:43:24,606]".replaceAll("\\[.*\\]", "").trim());
        System.out.println(StringUtils.removeEnd("A|B|", "|"));

        Pattern pattern4 = Pattern.compile("b2_node1.log\\z|(b2_postprocess.log.1\\z)");
        System.out.println(pattern4.matcher("b2_node1.log.1").find());
        System.out.println(pattern4.matcher("b2_node1.log").find());
        System.out.println(pattern4.matcher("b2_postprocess.log").find());

    }

}
