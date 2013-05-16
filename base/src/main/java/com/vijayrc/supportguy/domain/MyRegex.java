package com.vijayrc.supportguy.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Data
public class MyRegex {
    private String name;
    private String regex;
    private String dateFormat;
    private Pattern pattern;
    private List<String> groups = new ArrayList<String>();

    public MyRegex compile() {
        pattern = Pattern.compile(regex);

        return this;
    }


    public MyMatcher on(String line) {
        return new MyMatcher(this, pattern.matcher(line));
    }

}
