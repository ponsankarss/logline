package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Line;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllLines {
    private File file;
    private List<String> fileLines = new ArrayList<String>();
    private List<String> processedLines = new ArrayList<String>();
    private Set<Line> keyLines = new HashSet<Line>();
    private Set<Line> errorLines = new HashSet<Line>();

    public AllLines addFileLinesFrom(File file) throws Exception {
        this.file = file;
        this.fileLines = FileUtils.readLines(file);
        return this;
    }

    public AllLines addProcessedLine(String processedLine) {
        this.processedLines.add(processedLine);
        return this;
    }

    public AllLines addKeyLine(Line keyLine) {
        this.keyLines.add(keyLine.ofFile(file()));
        return this;
    }

    public AllLines addErrorLine(Line errorLine) {
        this.errorLines.add(errorLine);
        return this;
    }

    public AllLines flushFileLines() {
        this.fileLines.clear();
        return this;
    }

    public AllLines flushProcessedLines() {
        this.processedLines.clear();
        return this;
    }

    public String file() {
        return file.getName();
    }

    public Set<Line> keyLines() {
        return keyLines;
    }

    public Set<Line> errorLines() {
        return errorLines;
    }

    public List<String> fileLines() {
        return this.fileLines;
    }

    public List<String> processedLines() {
        return processedLines;
    }
}
