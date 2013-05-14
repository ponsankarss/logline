package com.vijayrc.supportguy.config;

import java.util.regex.Pattern;

public interface Constants {
    String formatForLongLogTimeWindow = "MM/dd/yyyy HH:mm:ss";
    String formatForShortLogTimeWindow = "MM/dd/yyyy";

    String userDir = System.getProperty("user.dir");


    //TODO move them out to json
    String logPattern1 = "yyyy-MM-dd HH:mm:ss,SSS";
    String logPattern2 = "dd MMM yyyy HH:mm:ss";
    Pattern dateRegex1 = Pattern.compile("(?<timestamp>[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2},[0-9]{0,3})]\\s*(?<thread>.*)\\s*(INFO|WARN|FATAL|ERROR|DEBUG)");
    Pattern dateRegex2 = Pattern.compile("[0-9]{2}\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*[0-9]{4}\\s*[0-9]{1,2}:[0-9]{2}:[0-9]{2}");
    int context = 30;
}
