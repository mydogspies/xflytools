package com.mydogspies.xflytools.controller;

import com.mydogspies.xflytools.controller.elements.LightToggleButton;

/**
 * This interface is used to extend ControllerCo with module-specific getters for UI elements.
 * See ControllerCo for more in-depth explanation.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @since 0.4.0
 */
public interface LightButtonController extends ControllerCo {

    public LightToggleButton getNavLight();

    public LightToggleButton getTaxiLight();

    public LightToggleButton getBeaconLight();

    public LightToggleButton getStrobeLight();

    public LightToggleButton getLandingLight();

}
