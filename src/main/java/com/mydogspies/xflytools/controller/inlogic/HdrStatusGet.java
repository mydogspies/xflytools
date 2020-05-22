package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import javafx.scene.control.ToggleButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This method checks if the HDR mode is set in the Xplane graphic settings and enables the FlashlightGet function if so.
 * Note that FlashlightGet only works if Xplane graphic settings are in HDR mode.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see MainWindowController
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class HdrStatusGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(HdrStatusGet.class);


    @Override
    public void execute(String command, ArrayList<String> values) {

        String value = values.get(0);

        final MainWindowController controller = MainWindowControllerSingleton.getInstance().getController();
        AtomicBoolean hdrStatus = controller.getHdrStatus();
        ToggleButton flashLight = controller.getFlashLight();

        if (value.equals("1")) {
            hdrStatus.set(true);
            flashLight.setDisable(false);
            log.trace("execute(): [" + command + "] -> HDR status is " + value + " [ON]");
        } else {
            hdrStatus.set(false);
            flashLight.setDisable(true);
            log.trace("execute(): [" + command + "] -> HDR status is " + value + " [OFF]");
            log.trace("execute(): [" + command + "] -> FlashlightGet is DISABLED in Xplane due to the HDR option not being on.");
        }
    }
}
