package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation of the command pattern for the autopilot vertical speed setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class APVerticalSpeedSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APVerticalSpeedSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apVSField = controller.getApVSField();
        String val = apVSField.getText();

        if (val.matches("[0-9]{3,4}")) {
            main_controller.sendToXplane("set", "ap_vertical_speed", val);
            log.trace("execute(): A/P vertical speed set to " + val + " in Xplane.");
        }
    }
}
