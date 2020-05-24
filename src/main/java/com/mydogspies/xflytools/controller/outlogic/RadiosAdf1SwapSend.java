package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the Adf 1 radio swap button in Xflytools.
 * In this case we simply use the command dataref in Xplane that swaps a radio between current and standby.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class RadiosAdf1SwapSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosAdf1SwapSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

        main_controller.sendToXplane("cmd", "adf1_flip", "");
        log.trace("toggleRadio(): Adf1 frequencies flipped.");
    }
}
