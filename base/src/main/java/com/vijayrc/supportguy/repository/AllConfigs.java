package com.vijayrc.supportguy.repository;

import com.google.gson.Gson;
import com.vijayrc.supportguy.meta.ConfigClass;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Scope("singleton")
public class AllConfigs {

    @Autowired
    private AllMachines allMachines;
    @Autowired
    private AllUsers allUsers;

    public void init() {
        Reflections reflections = new Reflections("com.vijayrc.supportguy");

        List<ConfigClass> annotations = new ArrayList<ConfigClass>();
        for (Class<?> configClass : reflections.getTypesAnnotatedWith(ConfigClass.class))
            annotations.add(configClass.getAnnotation(ConfigClass.class));

        System.out.println(annotations.size());
//        Collections.sort(annotations, new ConfigComparator());

        for (ConfigClass annotation : annotations) {
            System.out.println(annotation.file());
        }

        Gson gson = new Gson();
    }


    private class ConfigComparator implements Comparator<ConfigClass> {

        @Override
        public int compare(ConfigClass o1, ConfigClass o2) {
            return o1.order() >= o2.order() ? 1 : 0;
        }
    }


}
