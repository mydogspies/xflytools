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
 * The actual implementation of the command pattern for the A/P Altitude & V/S modes in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APAltitudeModeGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APAltitudeModeGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apAltitudeBtn = controller.getApAltitudeBtn();
        AutoPilotButton apVSBtn = controller.getApVSBtn();

        if (values.get(0).equals("6")) {
            apAltitudeBtn.setSelected(true);
            apVSBtn.setSelected(false);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in Altitude mode");
        } else if (values.get(0).equals("4")) {
            apAltitudeBtn.setSelected(false);
            apVSBtn.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in V/S mode");
        }
    }
}
