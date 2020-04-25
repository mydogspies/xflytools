package com.mydogspies.xflytools.data;

import java.util.List;

/**
 * This is a list of all of our drefdata objects
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class DrefDataContainer {

    private List<DrefData> drefdata;

    public DrefDataContainer() {}

    public DrefDataContainer(List<DrefData> drefdata) {
        this.drefdata = drefdata;
    }

    public List<DrefData> getDrefdata() {
        return drefdata;
    }

    public void setDrefdata(List<DrefData> drefdata) {
        this.drefdata = drefdata;
    }
}
