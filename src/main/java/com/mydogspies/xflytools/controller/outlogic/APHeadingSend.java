package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.APReadoutsControllerSingleton;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the implementation of the command pattern for the autopilot heading setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class APHeadingSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(APHeadingSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadouts controller = (APReadouts) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apHeadingField = controller.getApCourseField();
        String val = apHeadingField.getText();

        if (val.matches("[0-9]{3}")) {
            main_controller.sendToXplane("set", "ap_heading", val);
            log.trace("addToField(): A/P Heading course set to " + val + " in Xplane.");
        }
    }
}
