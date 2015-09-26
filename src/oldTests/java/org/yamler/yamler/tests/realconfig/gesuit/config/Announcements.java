package org.yamler.yamler.tests.realconfig.gesuit.config;


import org.yamler.yamler.Config;
import org.yamler.yamler.tests.realconfig.gesuit.config.sub.AnnouncementEntry;

import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Announcements extends Config {
    public Boolean Enabled = true;
    public HashMap<String, AnnouncementEntry> Announcements = new HashMap<>();
}
