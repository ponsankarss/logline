package com.vijayrc.supportguy.domain;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class Lines {
    private File file;
    private Set<Line> keyLines = new HashSet<Line>();
    private Set<Line> errorLines = new HashSet<Line>();
    private List<String> fileLines = new ArrayList<String>();
    private List<String> processedLines = new ArrayList<String>();

    public Lines addFileLinesFrom(File file) throws Exception {
        this.file = file;
        this.fileLines = FileUtils.readLines(file);
        return this;
    }

    public Lines addProcessedLine(String processedLine) {
        this.processedLines.add(processedLine);
        return this;
    }

    public Lines addKeyLine(Line keyLine) {
        this.keyLines.add(keyLine.ofFile(file()));
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

    public Map<String, List<Line>> byThread() {
        Map<String, List<Line>> groups = new HashMap<>();
        for (Line line : keyLines) {
            if (!groups.containsKey(line.getThread()))
                groups.put(line.getThread(), new ArrayList<Line>());
            groups.get(line.getThread()).add(line);
        }
        for (List<Line> lines : groups.values())
            Collections.sort(lines);
        return groups;
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

}

