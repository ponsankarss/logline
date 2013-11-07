package com.vijayrc.supportguy.util;

public class Util {

    public static String resource(String fileName) {
        boolean packaged = false;
        return packaged ? userDir() + fileName : ClassLoader.getSystemResource(fileName).getFile();
    }

    public static String userDir() {
        return System.getProperty("user.dir") + fileSeparator();
    }

    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }
}
