package com.vijayrc.supportguy.remote;

import java.util.List;
import java.util.regex.Pattern;

public interface MyRemote {
    MyRemote connect() throws Exception;
    MyRemote disconnect() throws Exception;
    List<String> browse(String sourcePath, Pattern pattern) throws Exception;
    void download(String sourcePath, String targetPath, Boolean recurse, Pattern pattern) throws Exception;
}
