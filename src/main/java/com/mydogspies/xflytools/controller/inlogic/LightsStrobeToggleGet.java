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
 * The actual implementation of the command pattern for strobe light switch in default Xplane.
 * This method receives from Xplane via the observers and updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class LightsStrobeToggleGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(LightsStrobeToggleGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final LightButtonController controller = (LightButtonController) LightButtonControllerSingleton.getInstance().getController();
        LightToggleButton strobeLight = controller.getStrobeLight();

        if (strobeLight.selectedProperty().getValue().equals(true) && values.get(0).equals("0")) {
            strobeLight.setSelected(false);
            log.trace("execute(): [" + command + "] -> " + values + " | strobe lights turned OFF in app.");
        } else if (strobeLight.selectedProperty().getValue().equals(false) && values.get(0).equals("1")) {
            strobeLight.setSelected(true);
            log.trace("execute(): [" + command + "] -> " + values + " | strobe lights turned ON in app.");
        }
    }
}
