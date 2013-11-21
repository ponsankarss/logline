package com.vijayrc.supportguy.domain;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class Lines {
    private File file;
    private List<String> fileLines = new ArrayList<>();
    private List<String> processedLines = new ArrayList<>();
    private Set<Line> errorLines = new HashSet<>();
    private ContextList contextList= new ContextList();

    public Lines addFileLinesFrom(File file) throws Exception {
        this.file = file;
        this.fileLines = FileUtils.readLines(file);
        return this;
    }

    public Lines addProcessedLine(String processedLine) {
        this.processedLines.add(processedLine);
        return this;
    }

    public Lines addErrorLine(Line errorLine) {
        this.errorLines.add(errorLine);
        return this;
    }

    public Lines flushFileLines() {
        this.fileLines.clear();
        return this;
    }

    public Lines flushProcessedLines() {
        this.processedLines.clear();
        return this;
    }

    public String file() {
        return file.getName();
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

    public Map<String, List<Line>> byError() {
        Map<String, List<Line>> groups = new HashMap<>();
        for (Line line : errorLines) {
            if (!groups.containsKey(line.errorTitle()))
                groups.put(line.errorTitle(), new ArrayList<Line>());
            groups.get(line.errorTitle()).add(line);
        }
        for (List<Line> lines : groups.values())
            Collections.sort(lines);
        return groups;
    }

    public void addContext(Context context) {
       contextList.add(context);
    }

    public List<Context> byContext(){
        return contextList.consolidate();
    }
}

