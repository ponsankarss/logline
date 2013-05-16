package com.vijayrc.supportguy.domain;

import difflib.*;
import difflib.Delta;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDiffTest {

    @Test
    public void shouldCompareFiles() throws IOException {
        File file1 = new File(this.getClass().getResource("/file1.txt").getFile());
        File file2 = new File(this.getClass().getResource("/file2.txt").getFile());
        List<String> original = FileUtils.readLines(file1);
        List<String> revised = FileUtils.readLines(file2);

        Patch patch = DiffUtils.diff(original, revised);

        for (Delta delta : patch.getDeltas()) {
            System.out.println(delta);
        }
    }
}
