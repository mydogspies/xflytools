package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the Com 1 radio frequency in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class RadiosCom1FreqSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosCom1FreqSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();

        RadioTextField com1Text = controller.getCom1Text();

        String val = com1Text.getText();
        if (controller.matchesFreqFormat(val, 3)) {
            val = controller.formatFreqToSend(val, 3);
            main_controller.sendToXplane("set", "com1_freq", val);
            log.trace("addToField(): Com1 active set to " + val + " in Xplane.");
        }
    }
}
