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
 * The actual implementation of the command pattern for the A/P NAV & APPR modes in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APApprStatusGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APApprStatusGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apNavBtn = controller.getApNavBtn();
        AutoPilotButton apApprBtn = controller.getApApprBtn();
        AutoPilotButton apHeadingBtn = controller.getApHeadingBtn();

        if (values.get(0).equals("1")) {
            apApprBtn.setSelected(true);
            apNavBtn.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in NAV + APPR mode");
        } else if (values.get(0).equals("0")) {
            apApprBtn.setSelected(false);
            if (apHeadingBtn.selectedProperty().getValue().equals(true)) {
                apNavBtn.setSelected(false);
            }
            log.trace("execute(): [" + command + "] -> " + values + " | A/P APPR mode OFF");
        } else if (values.get(0).equals("2")) {
            apApprBtn.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P APPR mode ON");
        }
    }
}
