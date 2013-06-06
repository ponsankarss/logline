package com.vijayrc.supportguy.domain;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        return hasGroup(name) ? matcher.group(name) : null;
    }

    public boolean hasGroup(String name) {
        return match && myRegex.hasGroup(name);
    }

    @Override
    public String toString() {
        return "[myRegex=" + myRegex + "|match=" + match + "]";
    }

    public boolean notMatched() {
        return !isMatch();
    }

    public DateTime getTime() throws ParseException {
        try {
            Date date = myRegex.getDateFormatter().parse(matcher.group("timestamp"));
            return new DateTime(date);
        } catch (ParseException e) {
            System.out.println(myRegex.getDateFormatter().toPattern()+"|"+matcher.group("timestamp"));
            return null;
        }
    }
}
