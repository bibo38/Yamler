package org.yamler.yamler.tests;

import org.yamler.yamler.Comment;
import org.yamler.yamler.Config;
import org.yamler.yamler.Path;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PathConfig extends Config {
    @Comment("test")
    @Path("config-with-dash")
    public Boolean Test = true;
}
