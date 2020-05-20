package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.APReadoutsControllerSingleton;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation of the command pattern for the autopilot altitude setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class APAltitudeSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APAltitudeSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadouts controller = (APReadouts) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apAltitudeField = controller.getApAltitudeField();
        String val = apAltitudeField.getText();

        if (val.matches("[0-9]{3,5}")) {
            main_controller.sendToXplane("set", "ap_altitude", val);
            log.trace("addToField(): A/P Altitude set to " + val + " in Xplane.");
        }

    }
}
