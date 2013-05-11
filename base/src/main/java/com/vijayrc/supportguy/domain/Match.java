package com.vijayrc.supportguy.domain;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

    public static boolean isMatch(Pattern pattern, String string) {
        if (pattern == null || StringUtils.isEmpty(string)) return false;
        Matcher matcher = pattern.matcher(string);
        return matcher != null && matcher.find();
    }
}
