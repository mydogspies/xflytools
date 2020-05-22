package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the barometric display value in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class MiscBaroSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(MiscBaroSend.class);

    @Override
    public void execute() {

        final MiscController controller = (MiscController) MiscControllerSingleton.getInstance().getController();
        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

        TextField baroField = controller.getBaroField();

        String val = baroField.getText();
        if (val.matches("[2-3][0-9]\\.[0-9]{2}")) {
            main_controller.sendToXplane("set", "baro_pilot_inhg", val);
            log.trace("addToField(): Baro set to " + val + " in Xplane.");
        } else if (val.matches("[1][0][0-9]{2}")) {
            val = controller.convertToInchesHG(val);
            main_controller.sendToXplane("set", "baro_pilot_inhg", val);
            log.trace("addToField(): Baro set to " + val + " in Xplane.");
        }
    }
}
