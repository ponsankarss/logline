package com.vijayrc.supportguy.domain;

import java.util.regex.Matcher;

public class MyMatcher {
    private MyRegex myRegex;
    private Matcher matcher;
    private boolean match;

    public MyMatcher() {
        this.match = false;
    }

    public MyMatcher(MyRegex myRegex, Matcher matcher) {
        this.myRegex = myRegex;
        this.matcher = matcher;
        this.match = matcher != null && matcher.find();
    }

    public boolean isMatch() {
        return match;
    }

    public String group(String name) {
        return hasGroup(name)? matcher.group(name):null;
    }

    public boolean hasGroup(String name) {
        return match && myRegex.hasGroup(name);
    }

    @Override
    public String toString() {
        return "[myRegex=" + myRegex + "|match=" + match + "]";
    }
}
