package com.vrc.logline;

import com.vrc.logline.controller.HomeController;
import com.vrc.logline.processor.XMLProcessor;
import com.vrc.logline.repository.AllLines;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertNotNull;

public class XMLProcessorTest {

    private XMLProcessor xmlProcessor;

    @Before
    public void setUp() {
        this.xmlProcessor = new XMLProcessor();
    }

    @Test
    public void shouldGetXML() throws Exception {
        String path = this.getClass().getResource("/postprocess.txt").getFile();
        AllLines allLines = new AllLines();
        allLines.addFileLinesFrom(new File(path));

        xmlProcessor.process(allLines);
        for (String s : allLines.processedLines()) {
            System.out.println(s);
        }
    }

    @Test
    public void instantiate() throws IllegalAccessException, InstantiationException {
        HomeController homeController = HomeController.class.newInstance();
        assertNotNull(homeController);
    }

}
