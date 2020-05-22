package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the barometric display to be set to standard pressure in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class MiscBaroStandardSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(MiscBaroStandardSend.class);

    @Override
    public void execute() {

        final MiscController controller = (MiscController) MiscControllerSingleton.getInstance().getController();
        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

        boolean baroSettingInHg = controller.isBaroSettingInHg();
        TextField baroField = controller.getBaroField();

        main_controller.sendToXplane("set", "baro_pilot_inhg", "29.92");
        if (baroSettingInHg) {
            baroField.setText("29.92");
            log.trace("addToField(): Baro set to STD (29.92 inHg) in Xplane.");
        } else {
            baroField.setText("1013");
            log.trace("addToField(): Baro set to STD (1013 mb) in Xplane.");
        }
    }
}
