package com.mydogspies.xflytools.controller.inlogic;

import java.util.Map;

public class InCommandMap {

    private Map<String, InCommand> InCommandMap;

    public InCommandMap(Map<String, InCommand> InCommandMap) {
        this.InCommandMap = InCommandMap;
    }

    public Map<String, InCommand> getInCommandMap() {
        return InCommandMap;
    }

    public void setInCommandMap(Map<String, InCommand> inCommandMap) {
        this.InCommandMap = inCommandMap;
    }
}
