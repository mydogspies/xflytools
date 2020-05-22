package com.mydogspies.xflytools.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * This interface is used to extend ControllerCo with module-specific getters for UI elements.
 * In this case we also define some methods internal to the implementation of the Misc class.
 * See ControllerCo for more in-depth explanation.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @since 0.4.0
 */
public interface MiscController extends ControllerCo {

    /* METHODS */

    public String convertToInchesHG(String mbar);

    public String convertToMillibar(String inhg);

    public String formatToInHG(String raw_inhg);

    /* JFX ELEMENTS */

    public ToggleButton getPressType();

    public Button getPressStd();

    public TextField getBaroField();

    public boolean isBaroSettingInHg();

    public void setBaroSettingInHg(boolean baroSettingInHg);
}
