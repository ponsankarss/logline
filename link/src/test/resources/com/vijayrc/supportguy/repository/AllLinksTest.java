package com.vijayrc.supportguy.repository;


import com.vijayrc.supportguy.domain.Link;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class AllLinksTest {

    private AllLinks allLinks;

    @Before
    public void setup() throws Exception {
        allLinks = new AllLinks();
    }

    @Test
    public void shouldFilterByNameAndEnv(){
        Link link = allLinks.getFor("remediation", "b2-p5-dev");
        assertNotNull(link);
        assertEquals("http://nasnmasdev:9107/blue2_plan_remediationWeb/b2remediation/transfer/show/",link.getUrl());
    }
}
