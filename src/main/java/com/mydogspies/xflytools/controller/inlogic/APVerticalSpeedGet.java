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
 * The actual implementation of the command pattern for the auto pilot vertical speed in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APVerticalSpeedGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APVerticalSpeedGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();
        AutoPilotLabel apVerticalSpeed = controller.getApVerticalSpeed();

        String val = values.get(0);
        if (!apVerticalSpeed.getText().equals(val)) {
            apVerticalSpeed.setText(val);
        }
        log.trace("execute(): [" + command + "] -> AP set to vertical speed " + val);

    }
}
