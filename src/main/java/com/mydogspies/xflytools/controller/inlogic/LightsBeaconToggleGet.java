package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.LightButtonController;
import com.mydogspies.xflytools.controller.LightButtonControllerSingleton;
import com.mydogspies.xflytools.controller.elements.LightToggleButton;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for beacon light switch in default Xplane.
 * This method receives from Xplane via the observers and updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class LightsBeaconToggleGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(LightsBeaconToggleGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final LightButtonController controller = (LightButtonController) LightButtonControllerSingleton.getInstance().getController();
        LightToggleButton beaconLight = controller.getBeaconLight();

        if (beaconLight.selectedProperty().getValue().equals(true) && values.get(0).equals("0")) {
            beaconLight.setSelected(false);
            log.trace("execute(): [" + command + "] -> " + values + " | beacon light turned OFF in app.");
        } else if (beaconLight.selectedProperty().getValue().equals(false) && values.get(0).equals("1")) {
            beaconLight.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | beacon light turned ON in app.");
        }
    }
}
