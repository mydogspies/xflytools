package com.mydogspies.xflytools.controller;

import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.elements.SwapButton;

/**
 * This interface is used to extend ControllerCo with module-specific getters for UI elements.
 * See ControllerCo for more in-depth explanation.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @since 0.4.0
 */
public interface RadiosController extends ControllerCo {

    /* METHODS */

    boolean matchesFreqFormat(String value, int maxDecimals);

    String formatFreqToSend(String value, int numberOfDecimals);

    /* JFX ELEMENTS */

    public RadioTextField getTransponder();

    public RadioTextField getCom1Text();

    public RadioTextField getCom2Text();

    public RadioTextField getNav1Text();

    public RadioTextField getNav2Text();

    public RadioTextField getAdf1Text();

    public RadioTextField getCom1Stby();

    public RadioTextField getCom2Stby();

    public RadioTextField getNav1Stby();

    public RadioTextField getNav2Stby();

    public RadioTextField getAdf1Stby();

    public SwapButton getCom1swap();

    public SwapButton getCom2swap();

    public SwapButton getNav1swap();

    public SwapButton getNav2swap();

    public SwapButton getAdf1swap();
}
