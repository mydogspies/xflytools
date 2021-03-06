package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the Nav 2 radio frequency in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class RadiosNav2FreqSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosNav2FreqSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();

        RadioTextField nav2Text = controller.getNav2Text();

        String val = nav2Text.getText();
        if (controller.matchesFreqFormat(val, 2)) {
            val = controller.formatFreqToSend(val, 2);
            main_controller.sendToXplane("set", "nav2_freq", val);
            log.trace("addToField(): Nav2 active set to " + val + " in Xplane.");
        }
    }
}
