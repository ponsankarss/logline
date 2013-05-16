package com.vijayrc.supportguy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.grep4j.core.result.GrepResults;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.on;

public class OptionsTest {

    //whole file leads to heap space bomb
    @Test
    public void shouldTestPerformanceWithWholeFileReading() throws IOException {
        File dir = new File("C:\\Users\\vchakrav\\Documents\\b2-errors\\prod-abends");
        Pattern pattern = Pattern.compile("74820130210002100|FZCTF2");
        Matcher matcher = null;
        StopWatch watch = new StopWatch();
        watch.start();
        List<String> lines = new ArrayList<String>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            System.out.println("processing " + file.getName());
            String fileContent = FileUtils.readFileToString(file);
            matcher = pattern.matcher(fileContent);
            while (matcher.find())
                lines.add(matcher.toMatchResult().toString());
        }
        watch.stop();
        System.out.println("Matched " + lines.size() + "|" + watch);
    }

    //fine ..takes 13-20 sec for 2 keys, 700MB
    @Test
    public void shouldTestPerformanceWithLineByLineReading() throws IOException {
        File dir = new File("C:\\Users\\vchakrav\\Documents\\b2-errors\\prod-abends");
        Pattern pattern = Pattern.compile("74820130210002100|FZCTF2|securityb2-780home");
        Matcher matcher = null;
        StopWatch watch = new StopWatch();
        watch.start();

        List<String> lines = new ArrayList<String>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            System.out.println("processing " + file.getName());
            List<String> fileLines = FileUtils.readLines(file);
            for (String fileLine : fileLines) {
                matcher = pattern.matcher(fileLine);
                if (matcher.find())
                    lines.add(fileLine);
            }
        }
        watch.stop();
        System.out.println("Matched " + lines.size() + "|" + watch);
        watch.reset();
    }

    //not doing better than java 7
    @Test
    public void shouldTestPerformanceWitJRegex() throws IOException {
        File dir = new File("C:\\Users\\vchakrav\\Documents\\b2-errors\\prod-abends");
        jregex.Pattern pattern = new jregex.Pattern("74820130210002100|FZCTF2");
        jregex.Matcher matcher = null;
        StopWatch watch = new StopWatch();
        watch.start();

        List<String> lines = new ArrayList<String>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            System.out.println("processing " + file.getName());
            List<String> fileLines = FileUtils.readLines(file);
            for (String fileLine : fileLines) {
                matcher = pattern.matcher(fileLine);
                if (matcher.find())
                    lines.add(fileLine);
            }
        }
        watch.stop();
        System.out.println("Matched " + lines.size() + "|" + watch);
        watch.reset();
    }

    @Test
    public void shouldUseGrep4J() {
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("Local server log")
                .filePath("C:/Work/test/b2_node1.txt")
                .onLocalhost()
                .build();

        GrepResults results = grep(constantExpression("30320123170618600"), on(localProfile));
        System.out.println("Grep results : " + results);
        System.out.println("Total lines found : " + results.totalLines());
        System.out.println("Total Execution Time : " + results.getTotalExecutionTime());
    }

}
