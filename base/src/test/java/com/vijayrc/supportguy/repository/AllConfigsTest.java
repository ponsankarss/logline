package com.vijayrc.supportguy.repository;

import org.junit.Test;

public class AllConfigsTest{

    private AllConfigs allConfigs = new AllConfigs();
    @Test
    public void shouldLoadConfiguration(){
        allConfigs.init();
    }
}
