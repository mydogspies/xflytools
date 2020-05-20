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
 * The actual implementation of the command pattern for the autopilot/nav1 CRS setting in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @since 0.4.0
 */
public class Nav1CourseSend implements OutCommand{

    private static final Logger log = LoggerFactory.getLogger(Nav1CourseSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final APReadouts controller = (APReadouts) APReadoutsControllerSingleton.getInstance().getController();

        AutoPilotField apCourseField = controller.getApCourseField();
        String val = apCourseField.getText();

        if (val.matches("[0-9]{3}")) {
            main_controller.sendToXplane("set", "nav1_course", val);
            log.trace("addToField(): Nav1 course set to " + val + " in Xplane.");
        }
    }
}
