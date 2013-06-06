package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Machine;
import com.vijayrc.supportguy.domain.Tailer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.hamcrest.Matchers.equalTo;

@Repository
@Scope("singleton")
public class AllTailers {

    private List<Tailer> tailers = new ArrayList<>();

    public void add(Tailer tailer) {
        tailers.add(tailer);
    }

    public List<String> allNames() {
        return extract(tailers, on(Tailer.class).name());
    }

    public Tailer find(String tailName) {
        List<Tailer> list = filter(having(on(Tailer.class).name(), equalTo(tailName)), tailers);
        return isNotEmpty(list) ? list.get(0) : null;
    }
}
