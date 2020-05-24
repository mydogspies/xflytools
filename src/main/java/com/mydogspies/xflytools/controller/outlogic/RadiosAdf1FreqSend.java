package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the Adf 1 radio frequency in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class RadiosAdf1FreqSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosAdf1FreqSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();

        RadioTextField adf1Text = controller.getAdf1Text();

        String val = adf1Text.getText();
        if (controller.matchesFreqFormat(val, 0)) {
            val = controller.formatFreqToSend(val, 0);
            main_controller.sendToXplane("set", "adf1_freq", val);
            log.trace("addToField(): Adf1 set to " + val + " in Xplane.");
        }
    }
}
