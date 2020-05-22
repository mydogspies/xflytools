package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.AutoPilotButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the autopilot on/off function button in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class APModeSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APModeSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apToggleBtn = controller.getApToggleBtn();

        if (apToggleBtn.selectedProperty().getValue().equals(false)) {
            controller.disableAll(true);
            main_controller.sendToXplane("set", "ap_mode", "0");
            log.trace("execute(): A/P is OFF");
        } else {
            controller.disableAll(false);
            main_controller.sendToXplane("set", "ap_mode", "2");
            log.trace("execute(): A/P is ON");
        }
    }
}
