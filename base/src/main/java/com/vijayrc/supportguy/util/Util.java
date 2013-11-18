package com.vijayrc.supportguy.util;

import lombok.extern.log4j.Log4j;

@Log4j
public class Util {

    public static String resource(String fileName) {
        boolean packaged = false;
        if(packaged){
            return userDir() + fileName;
        }else{
            return ClassLoader.getSystemResource(fileName).getFile();
        }
    }

    public static String userDir() {
        return System.getProperty("user.dir") + fileSeparator();
    }

    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }
}
