package org.yamler.yamler.tests;

import org.yamler.yamler.Config;
import org.yamler.yamler.ConfigMode;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FieldIsKeyConfig extends Config {
    public FieldIsKeyConfig() {
        CONFIG_MODE = ConfigMode.FIELD_IS_KEY;
    }

    public String MOTD = "&dWelcome to the server {player}!";
    public String PLAYER_CONNECT_PROXY = "{player}&e has joined the server!";
}
