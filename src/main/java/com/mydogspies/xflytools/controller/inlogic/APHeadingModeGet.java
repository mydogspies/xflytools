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
 * The actual implementation of the command pattern for the A/P NAV modes in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APHeadingModeGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APHeadingModeGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APButtonsController controller = (APButtonsController) APButtonControllerSingleton.getInstance().getController();
        AutoPilotButton apHeadingBtn = controller.getApHeadingBtn();
        AutoPilotButton apNavBtn = controller.getApNavBtn();

        if (values.get(0).equals("1")) {
            apHeadingBtn.setSelected(true);
            apNavBtn.setSelected(false);
            apNavBtn.setText("Nav");
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in NAV mode");
        } else if (values.get(0).equals("2")) {
            apHeadingBtn.setSelected(false);
            apNavBtn.setSelected(true);
            apNavBtn.setText("Nav");
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in NAV mode");
        } else if (values.get(0).equals("13")) {
            apNavBtn.setText("GPSS");
            log.trace("execute(): [" + command + "] -> " + values + " | A/P in GPSS mode");
        }
    }
}
