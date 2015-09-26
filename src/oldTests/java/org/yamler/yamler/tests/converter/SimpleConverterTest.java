package org.yamler.yamler.tests.converter;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yamler.yamler.tests.base.BaseTest;
import org.yamler.yamler.tests.base.Util;
import org.yamler.yamler.tests.converter.config.SimpleObjectConfig;
import org.yamler.yamler.tests.converter.customconverter.ObjectConverter;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class SimpleConverterTest extends BaseTest {
    @BeforeSuite
    public void setup() throws Exception {
        config = new SimpleObjectConfig();
        config.addConverter(ObjectConverter.class);
        filename = "simpleConverterTest.yml";

        before();
    }

    @Test(priority = 1)
    public void onInit() throws Exception {
        config.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents,
                "TestMap:\n" +
                "  test: test\n" +
                "TestSet:\n" +
                "- test\n");
    }
}
