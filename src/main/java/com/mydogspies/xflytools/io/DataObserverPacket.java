package com.mydogspies.xflytools.io;

import java.util.ArrayList;

public class DataObserverPacket {

    private String dref;
    private ArrayList<String> values;

    public DataObserverPacket(String dref, ArrayList<String> values) {
        this.dref = dref;
        this.values = values;
    }

    public String getDref() {
        return dref;
    }

    public ArrayList<String> getValues() {
        return values;
    }
}
