package com.vijayrc.supportguy.repository;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.vijayrc.supportguy.domain.MyMatcher;
import com.vijayrc.supportguy.domain.MyRegex;
import com.vijayrc.supportguy.util.Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;

@Repository
@Scope("singleton")
public class AllLogRegex {

    private List<MyRegex> regexes = new ArrayList<MyRegex>();

    public AllLogRegex() throws Exception {
        String file = Util.resource("logs.yml");
        YamlReader reader = new YamlReader(new FileReader(file));
        while (true) {
            MyRegex myRegex = reader.read(MyRegex.class);
            if (myRegex == null) break;
            regexes.add(myRegex.compile());
        }
    }

    public MyRegex findFor(String name) {
        List<MyRegex> list = filter(having(on(MyRegex.class).getName(), equalTo(name)), regexes);
        return isNotEmpty(list) ? list.get(0) : null;
    }

    public MyMatcher findMatched(String line) {
        for (MyRegex regex : regexes) {
            MyMatcher myMatcher = regex.on(line);
            if (myMatcher.isMatch())
                return myMatcher;
        }
        return new MyMatcher();
    }


}
