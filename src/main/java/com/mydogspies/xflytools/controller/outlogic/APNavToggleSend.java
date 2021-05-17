package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.AutoPilotButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the autopilot NAV toggle button in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APButtons
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class APNavToggleSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APNavToggleSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();

        AutoPilotButton apRevBtn = controller.getApRevBtn();
        AutoPilotButton apNavBtn = controller.getApNavBtn();

        if (apRevBtn.selectedProperty().getValue().equals(true)) {
            apNavBtn.setSelected(true);
        }
        main_controller.sendToXplane("cmd", "ap_nav_toggle", "");
        log.trace("execute(): A/P set to Nav mode.");
    }
}
