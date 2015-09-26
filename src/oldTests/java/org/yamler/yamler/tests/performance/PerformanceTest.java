package org.yamler.yamler.tests.performance;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yamler.yamler.InvalidConfigurationException;
import org.yamler.yamler.tests.base.BaseTest;
import org.yamler.yamler.tests.performance.config.PerformanceConfig;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PerformanceTest extends BaseTest {
    @BeforeSuite
    public void setup() throws Exception {
        config = new PerformanceConfig();
        filename = "performanceConfig.yml";

        before();
    }

    @Test(priority = 1)
    public void loadPerformance() throws InvalidConfigurationException {
        long start = System.currentTimeMillis();

        for(int i = 0; i < 100; i++) config.init(file);

        long end = System.currentTimeMillis() - start;

        Assert.assertTrue(end < 20000, "" + end);
    }

    @Test(priority = 2)
    public void savePerformance() throws InvalidConfigurationException {
        long start = System.currentTimeMillis();

        for(int i = 0; i < 100; i++) config.save(file);

        long end = System.currentTimeMillis() - start;

        Assert.assertTrue(end < 20000, "" + end);
    }
}
