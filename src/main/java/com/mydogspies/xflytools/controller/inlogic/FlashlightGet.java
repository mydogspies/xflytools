package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import javafx.scene.control.ToggleButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of command pattern for the FlashlightGet function in Xplane.
 * Note that FlashlightGet only works if Xplane graphic settings are in HDR mode.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see MainWindowController
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class FlashlightGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(FlashlightGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        ToggleButton flashLight = main_controller.getFlashLight();

        if (values.get(0).equals("1")) {

            flashLight.setSelected(true);
            log.trace("execute(): [" + command + "] -> FlashlightGet is ON in Xplane.");
        } else if (values.get(0).equals("0")) {

            flashLight.setSelected(false);
            log.trace("execute(): [" + command + "] -> FlashlightGet is OFF in Xplane.");
        }

    }
}
