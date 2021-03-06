package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.APButtonControllerSingleton;
import com.mydogspies.xflytools.controller.APButtonsController;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.elements.AutoPilotButton;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the A/P on/off function in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APModeGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APModeGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apToggleBtn = controller.getApToggleBtn();

        if (apToggleBtn.selectedProperty().getValue().equals(true) && values.get(0).equals("0")) {
            apToggleBtn.setSelected(false);
            controller.disableAll(true);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P turned OFF in app.");
        } else if (apToggleBtn.selectedProperty().getValue().equals(false) && values.get(0).equals("2")) {
            apToggleBtn.setSelected(true);
            controller.disableAll(false);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P turned ON in app.");
        }
    }
}
