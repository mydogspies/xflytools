package com.mydogspies.xflytools.data;

/**
 * The data pojo for the json database that defines the window layout.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LayoutData {

    private String actProfile;
    private String path;
    private String dataset;

    public LayoutData () {}

    public LayoutData(String actProfile, String path, String dataset) {
        this.actProfile = actProfile;
        this.path = path;
        this.dataset = dataset;
    }

    public String getActProfile() {
        return actProfile;
    }

    public String getPath() {
        return path;
    }

    public String getDataset() {
        return dataset;
    }
}
