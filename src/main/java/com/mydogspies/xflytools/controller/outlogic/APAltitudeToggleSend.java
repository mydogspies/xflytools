package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the autopilot ALT toggle button in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APButtons
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class APAltitudeToggleSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APAltitudeToggleSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

        main_controller.sendToXplane("cmd", "ap_altitude_toggle", "");
        log.trace("execute(): A/P set to Altitude mode.");
    }
}
