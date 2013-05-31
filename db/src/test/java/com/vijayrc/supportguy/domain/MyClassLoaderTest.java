package com.vijayrc.supportguy.domain;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class MyClassLoaderTest {

    private MyClassLoader loader;

    @Before
    public void setup() throws Exception {
        URL urls [] = {};
        loader = new MyClassLoader(urls);
    }

    @Test
    public void shouldLoadAClass() throws ClassNotFoundException {
        loader.loadClass("org.postgresql.Driver");
    }


}
