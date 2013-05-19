package com.vijayrc.supportguy.repository;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AllRejectsTest {

    private AllRejects allRejects;

    @Test
    public void shouldReadFileAndMakeAnInvalidPattern() throws Exception {
        allRejects = new AllRejects();
        assertFalse(allRejects.matches("no no yes yes"));
        assertTrue(allRejects.matches("com.bcbsa.blue2.common.Blue2Exception: Invalid Action Code"));
    }
}
