package com.mydogspies.xflytools.data;

/**
 * The data pojo for the json database that defines the window layout.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LayoutData {

    private String actProfile;
    private String path;

    public LayoutData () {}

    public LayoutData(String actProfile, String path) {
        this.actProfile = actProfile;
        this.path = path;
    }

    public String getActProfile() {
        return actProfile;
    }

    public String getPath() {
        return path;
    }

}
