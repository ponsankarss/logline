package com.vijayrc.supportguy.domain;

import java.util.*;

public class NameGroup {
    private List<String> names;
    private List<String> titles;

    //TODO
    public NameGroup(List<String> names) {
        this.titles = new ArrayList<String>();
        this.titles.addAll(Arrays.asList("SystemOut", "postprocess", "router", "planInt", "remediation",
                "remoterequest", "node", "report", "termmessage", "SystemErr", "healthcheck", "native", "trace"));
        this.names = names;
    }

    public Map<String, List<String>> byName() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String name : names)
            for (String title : titles)
                if (name.contains(title)) {
                    if (!map.containsKey(title))
                        map.put(title, new ArrayList<String>());
                    map.get(title).add(name);
                }
        return map;
    }


}
