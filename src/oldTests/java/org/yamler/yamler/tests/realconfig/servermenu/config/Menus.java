package org.yamler.yamler.tests.realconfig.servermenu.config;

import org.yamler.yamler.Config;

import java.util.ArrayList;
import java.util.List;

public class Menus extends Config {
    private String title;
    private List<Servers> servers = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setServers(List<Servers> servers) {
        this.servers = servers;
    }
}
