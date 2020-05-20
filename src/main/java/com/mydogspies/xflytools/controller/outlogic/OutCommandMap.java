package com.mydogspies.xflytools.controller.outlogic;

import java.util.Map;

public class OutCommandMap {

    private Map<String, OutCommand> outCommandMap;

    public OutCommandMap(Map<String, OutCommand> outCommandMap) {
        this.outCommandMap = outCommandMap;
    }

    public Map<String, OutCommand> getOutCommandMap() {
        return outCommandMap;
    }

    public void setOutCommandMap(Map<String, OutCommand> outCommandMap) {
        this.outCommandMap = outCommandMap;
    }
}
