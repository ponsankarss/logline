package com.vijayrc.supportguy.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Delta {
    List<FileDiff> changed = new ArrayList<FileDiff>();
    List<FileDiff> missing = new ArrayList<FileDiff>();

    public void add(FileDiff fileDiff) {
        if (fileDiff.isMissing())
            missing.add(fileDiff);
        else
            changed.add(fileDiff);
    }
}
