package com.vijayrc.supportguy.domain;

import ch.lambdaj.group.Group;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static ch.lambdaj.group.Groups.by;
import static ch.lambdaj.group.Groups.group;

public class ContextList {
    private List<Context> list = new ArrayList<>();

    public void add(Context context){
        list.add(context);
    }

    public List<Context> consolidate(){
        List<Context> newList = new ArrayList<>();
        Group<Context> group = group(list, by(on(Context.class).startTime()));

        for (String key : group.keySet()) {
            List<Context> byStartTime = group.find(key);
            Context newContext = new Context(byStartTime.get(0).name(),3);
            for (Context context : byStartTime)
               newContext.addAll(context.lines());
            newList.add(newContext.consolidate());
        }
        list.clear();
        return sort(newList,on(Context.class).startTime());
    }
}
