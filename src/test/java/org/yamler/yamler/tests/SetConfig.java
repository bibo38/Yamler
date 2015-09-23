package org.yamler.yamler.tests;

import org.yamler.yamler.Config;

import java.util.HashSet;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class SetConfig extends Config {
    public java.util.Set<String> StringSet = new HashSet<String>(){{
        add("Test");
    }};
}
