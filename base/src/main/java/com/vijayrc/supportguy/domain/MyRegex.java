package com.vijayrc.supportguy.domain;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class MyRegex {
    private String name;
    private String regex;
    private String dateFormat;
    private Pattern pattern;

    public MyRegex initialize(){
        pattern = Pattern.compile(regex);
        return this;
    }

}
