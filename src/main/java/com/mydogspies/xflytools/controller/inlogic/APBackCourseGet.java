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
 * The actual implementation of the command pattern for the A/P back course in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APBackCourseGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APBackCourseGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apRevBtn = controller.getApRevBtn();
        AutoPilotButton apApprBtn = controller.getApApprBtn();

        if (values.get(0).equals("1")) {
            apRevBtn.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P Back Course ON");
        } else if (values.get(0).equals("0")) {
            apRevBtn.setSelected(false);
            apApprBtn.setSelected(false);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P Back Course OFF");
        }
    }
}
