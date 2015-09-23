package org.yamler.yamler.tests.converter.config;

import org.yamler.yamler.Config;
import org.yamler.yamler.tests.converter.ArrayConverterTest;

/**
 * This test config is based on the Issues #16 and #17
 * It provides tests for (multidimensional) arrays of primitive types,
 * Strings and convertable types.
 *
 * @author bibo38
 * @see ArrayConverterTest
 */
public class ArrayTestConfig extends Config {

    public class InnerConfig extends Config {
        public int x = 7;
    }

    public int[] data = { 1, 2, 3 };
    public double[][] multiArray = { { 1 }, { 2, 3 }, { 4 } };
    public InnerConfig[] configs = { new InnerConfig(),
            new InnerConfig(),
            new InnerConfig()
    };
    public String[] strings = { "Hello", "World", "!" };
}
