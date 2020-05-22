package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation of the command pattern for the autopilot heading setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see AddCommandMapData
 * @see ControllerCo
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts
 * @since 0.4.0
 */
public class APHeadingSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APHeadingSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apHeadingField = controller.getApHeadingField();
        String val = apHeadingField.getText();

        if (val.matches("[0-9]{3}")) {
            main_controller.sendToXplane("set", "ap_heading", val);
            log.trace("execute(): A/P Heading course set to " + val + " in Xplane.");
        }
    }
}
