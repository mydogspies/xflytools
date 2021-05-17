package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.APReadoutsController;
import com.mydogspies.xflytools.controller.APReadoutsControllerSingleton;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.elements.AutoPilotLabel;
import com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the auto pilot altitude in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @see APReadoutsController
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APAltitudeGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APAltitudeGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();
        AutoPilotLabel apLevel = controller.getApLevel();

        String val = values.get(0);
        if (!apLevel.getText().equals(val)) {
            apLevel.setText(val + "'");
        }
        log.trace("execute(): [" + command + "] -> AP set to altitude " + val);

    }
}
