package com.vijayrc.supportguy.util;

public class Util {
    private static boolean packaged = false;

    public static String resource(String fileName) {
        return packaged ? userDir() + fileName : ClassLoader.getSystemResource(fileName).getFile();
    }

    public static String userDir() {
        return System.getProperty("user.dir") + fileSeparator();
    }

    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }
}
