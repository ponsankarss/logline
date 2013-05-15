package com.vijayrc.supportguy.util;

public class Util {

    public static String resource(String fileName){
        return ClassLoader.getSystemResource(fileName).getFile();
    }

    public static String userDir(){
        return System.getProperty("user.dir");
    }
}
