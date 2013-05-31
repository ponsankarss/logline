package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class AllQueriesTest {

    @Autowired
    private AllQueries allQueries;

    @Test
    public void shouldLoadQueriesAlongWithDbs() {
        Query query = allQueries.findBy("blog-posts-count", "vectorclocks");
        assertNotNull(query);
        assertEquals("org.postgresql.Driver", query.getDatabase().getDriver());
    }
}
