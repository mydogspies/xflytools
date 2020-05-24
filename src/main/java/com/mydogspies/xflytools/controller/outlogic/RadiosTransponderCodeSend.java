package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.*;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the transponder frequency in Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class RadiosTransponderCodeSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosTransponderCodeSend.class);

    @Override
    public void execute() {

        final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();

        RadioTextField transponder = controller.getTransponder();

        String val = transponder.getText();
        if (val.matches("[0-9]{4}")) {
            main_controller.sendToXplane("set", "transponder_code", val);
            log.trace("addToField(): Transponder set to " + val + " in Xplane.");
        }
    }
}
