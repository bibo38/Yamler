package org.yamler.yamler.tests.extend;

import org.yamler.yamler.Config;

public class ActionConfig extends Config
{
    public ActionConfig()
    {
    }

    public ActionConfig(String action)
    {
        this.action = action;
    }

    public String action = "default action";
}