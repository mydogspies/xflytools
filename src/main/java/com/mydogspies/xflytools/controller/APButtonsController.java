package com.mydogspies.xflytools.controller;

import com.mydogspies.xflytools.controller.elements.AutoPilotButton;

/**
 * This interface is used to extend ControllerCo with module-specific getters for UI elements.
 * See ControllerCo for more in-depth explanation.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @since 0.4.0
 */
public interface APButtonsController extends ControllerCo {

    public AutoPilotButton getApToggleBtn();

    public AutoPilotButton getApHeadingBtn();

    public AutoPilotButton getApAltitudeBtn();

    public AutoPilotButton getApVSBtn();

    public AutoPilotButton getApApprBtn();

    public AutoPilotButton getApNavBtn();

    public AutoPilotButton getApRevBtn();
}
