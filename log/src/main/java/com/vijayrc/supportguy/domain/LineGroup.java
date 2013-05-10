package com.vijayrc.supportguy.domain;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.util.*;

public class LineGroup {
    private static final Logger log = Logger.getLogger(LineGroup.class);
    private Set<Line> lines;

    public LineGroup(Set<Line> lines) {
        this.lines = lines;
    }

    public Map<String, List<Line>> byThread() {
        StopWatch watch = new StopWatch();
        watch.start();
        Map<String, List<Line>> groups = new HashMap<String, List<Line>>();
        for (Line line : lines) {
            if (!groups.containsKey(line.getThread()))
                groups.put(line.getThread(), new ArrayList<Line>());
            groups.get(line.getThread()).add(line);
        }
        for (List<Line> lines : groups.values())
            Collections.sort(lines);
        watch.stop();
        log.info("Time taken to group: "+watch+"|groups="+groups.size());
        return groups;
    }

    public Map<String, Set<Line>> byTitle() {
        StopWatch watch = new StopWatch();
        watch.start();
        Map<String, Set<Line>> groups = new HashMap<String, Set<Line>>();
        for (Line line : lines) {
            if (!groups.containsKey(line.errorTitle()))
                groups.put(line.errorTitle(), new HashSet<Line>());
            groups.get(line.errorTitle()).add(line);
        }
        watch.stop();
        log.info("Time taken to group: " + watch + "|groups=" + groups.size());
        return groups;
    }

}
