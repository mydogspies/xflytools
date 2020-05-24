package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the Com 2 standby radio frequency in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class RadiosCom2StdbyFreqSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosCom2StdbyFreqSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();

        RadioTextField com2Stby = controller.getCom2Stby();

        String val = com2Stby.getText();
        if (controller.matchesFreqFormat(val, 3)) {
            val = controller.formatFreqToSend(val, 3);
            main_controller.sendToXplane("set", "com2_stdby_freq", val);
            log.trace("addToField(): Com2 standby set to " + val + " in Xplane.");
        }
    }
}
