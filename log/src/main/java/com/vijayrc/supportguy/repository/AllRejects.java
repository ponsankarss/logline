package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.util.Util;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import static ch.lambdaj.Lambda.join;

@Repository
@Scope("singleton")
public class AllRejects {

    private List<String> rejects;
    private Pattern pattern;

    public AllRejects() throws Exception {
        String file = Util.resource("log-rejects.yml");
        rejects = FileUtils.readLines(new File(file));
        String regex = join(rejects, "|");
        System.out.println(regex);
        pattern = Pattern.compile(regex);
    }

    public boolean matches(String line) {
        return pattern.matcher(line).find();
    }
}
