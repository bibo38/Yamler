package org.yamler.yamler.tests.converter;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yamler.yamler.Converter.Array;
import org.yamler.yamler.InvalidConfigurationException;
import org.yamler.yamler.tests.base.BaseTest;
import org.yamler.yamler.tests.converter.config.ArrayTestConfig;

/**
 * This test uses the {@link ArrayTestConfig} to check that
 * the {@link Array} converter
 * works in the correct way.
 *
 * @author bibo38
 * @see ArrayTestConfig The used config
 */
public class ArrayConverterTest extends BaseTest {
    /**
     * Sets the Test up, to provide the used configuration
     * and the filename for the test configuration.
     *
     * @throws Exception
     */
    @Override
    @BeforeSuite
    public void setup() throws Exception {
        config = new ArrayTestConfig();
        filename = "arrayConverterTest.yml";

        before();
    }

    /**
     * A basic test to confirm, that the used Config can be saved and loaded.
     *
     * @throws InvalidConfigurationException If the configuration cannot be loaded
     */
    @Test
    public void testInitLoad() throws InvalidConfigurationException {
        config.init(file);
        config.load();
    }
}
