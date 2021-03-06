package com.vijayrc.supportguy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class MyRegex {
    private String name;
    private String regex;
    private String dateFormat;
    private Pattern pattern;
    private Pattern groupPattern;
    private List<String> groups;
    private SimpleDateFormat dateFormatter;

    public MyRegex(String regex) {
        this.regex = regex;
    }

    public MyRegex compile() {
        pattern = Pattern.compile(regex);
        groupPattern = Pattern.compile("\\?<([a-z]+)>");
        groups = new ArrayList<>();
        Matcher matcher = groupPattern.matcher(regex);
        while (matcher.find())
            groups.add(matcher.group(1));

        if(StringUtils.isNotBlank(dateFormat)){
            dateFormatter = new SimpleDateFormat(dateFormat);
            dateFormatter.setLenient(true);
        }
        return this;
    }

    public boolean hasGroup(String name) {
        return groups.contains(name);
    }

    public MyMatcher on(String line) {
        return new MyMatcher(this, pattern.matcher(line));
    }

}
