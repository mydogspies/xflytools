package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation of the command pattern for the autopilot altitude setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class APAltitudeSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APAltitudeSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apAltitudeField = controller.getApAltitudeField();
        String val = apAltitudeField.getText();

        if (val.matches("[0-9]{3,5}")) {
            main_controller.sendToXplane("set", "ap_altitude", val);
            log.trace("execute(): A/P Altitude set to " + val + " in Xplane.");
        }
    }
}
