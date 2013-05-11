package com.vijayrc.supportguy.domain;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDiff {
    private static final Logger log = Logger.getLogger(FileDiff.class);

    private Pattern pattern = Pattern.compile("lines:\\s*\\[(?<change>.*?)\\]\\]");
    private Pattern changeExistPattern = Pattern.compile("[^(,\\s+)]+");

    private String name;
    private String result;
    private String filePath1;
    private String filePath2;
    private boolean missing;

    public FileDiff(String name, String filePath1, String filePath2) {
        this.name = name;
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
    }

    public FileDiff process() throws Exception {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        if (!file1.exists()) {
            missing = true;
        } else if (!file2.exists()) {
            missing = true;
        } else {
            Patch patch = DiffUtils.diff(FileUtils.readLines(file1), FileUtils.readLines(file2));
            StringBuffer buffer = new StringBuffer();
            for (Delta delta : patch.getDeltas()) {
                Matcher matcher = pattern.matcher(delta.toString());
                if (matcher.find() && changeExistPattern.matcher(matcher.group("change")).find())
                    buffer.append(StringUtils.deleteWhitespace(delta.toString()) + "\n");
            }
            result = buffer.toString();
            log.info(result);
        }
        return this;
    }

    public String result() {
        return result;
    }

    public boolean hasResult() {
        return StringUtils.isNotEmpty(result);
    }

    public String name() {
        return name;
    }

    public boolean isMissing(){
        return missing;
    }

}

