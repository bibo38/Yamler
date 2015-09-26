package org.yamler.yamler.tests.converter.config;

import org.yamler.yamler.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class SimpleObjectConfig extends Config {
    public java.util.Map TestMap = new HashMap<String, String>(){{
        put("test", "test");
    }};

    public Set TestSet = new HashSet<String>(){{
        add("test");
    }};
}
