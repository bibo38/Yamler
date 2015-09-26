package org.yamler.yamler.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yamler.yamler.InvalidConfigurationException;
import org.yamler.yamler.tests.base.Util;

import java.io.File;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FieldIsKey {
    private FieldIsKeyConfig fieldIsKeyConfig;
    private File file;

    @BeforeSuite
    public void before() {
        fieldIsKeyConfig = new FieldIsKeyConfig();

        file = new File("temp", "fieldIsKeyConfig.yml");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if(file.exists()) {
            file.delete();
        }
    }

    @Test(priority = 1)
    public void initNull() throws InvalidConfigurationException, IOException {
        fieldIsKeyConfig.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "MOTD: '&dWelcome to the server {player}!'\n" +
                "PLAYER_CONNECT_PROXY: '{player}&e has joined the server!'\n");
    }
}
