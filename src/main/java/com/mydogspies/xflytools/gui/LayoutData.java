package com.mydogspies.xflytools.gui;

/**
 * The data pojo for the json database that defines the window layout.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LayoutData {

    private String actProfile;
    private String windowGeneral;
    private String windowLightButtons;
    private String windowRadios;
    private String windowAPButtons;
    private String windowAPReadouts;

    public LayoutData () {}

    public LayoutData(String actProfile, String windowGeneral, String windowLightButtons, String windowRadios, String windowAPButtons, String windowAPReadouts) {
        this.actProfile = actProfile;
        this.windowGeneral = windowGeneral;
        this.windowLightButtons = windowLightButtons;
        this.windowRadios = windowRadios;
        this.windowAPButtons = windowAPButtons;
        this.windowAPReadouts = windowAPReadouts;
    }

    public String getActProfile() {
        return actProfile;
    }

    public void setActProfile(String actProfile) {
        this.actProfile = actProfile;
    }

    public String getWindowGeneral() {
        return windowGeneral;
    }

    public void setWindowGeneral(String windowGeneral) {
        this.windowGeneral = windowGeneral;
    }

    public String getWindowLightButtons() {
        return windowLightButtons;
    }

    public void setWindowLightButtons(String windowLightButtons) {
        this.windowLightButtons = windowLightButtons;
    }

    public String getWindowRadios() {
        return windowRadios;
    }

    public void setWindowRadios(String windowRadios) {
        this.windowRadios = windowRadios;
    }

    public String getWindowAPButtons() {
        return windowAPButtons;
    }

    public void setWindowAPButtons(String windowAPButtons) {
        this.windowAPButtons = windowAPButtons;
    }

    public String getWindowAPReadouts() {
        return windowAPReadouts;
    }

    public void setWindowAPReadouts(String windowAPReadouts) {
        this.windowAPReadouts = windowAPReadouts;
    }
}
