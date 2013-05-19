package com.vijayrc.supportguy.domain;

import lombok.Data;

import static com.vijayrc.supportguy.util.Util.fileSeparator;
import static com.vijayrc.supportguy.util.Util.userDir;

@Data
public class Scm {

    private String name;
    private String location;
    private String type;

    public String getFileFor(String peerFile) {
        return location + peerFile.replace(userDir() + "config" + fileSeparator(), "");
    }


}
