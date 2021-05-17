package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the taxi light toggle button in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class LightsTaxiToggleSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(LightsTaxiToggleSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

        main_controller.sendToXplane("cmd", "taxi_lights_flip", "");
        log.trace("execute(): Taxi lights toggled");
    }
}
