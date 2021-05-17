package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.controller.RadiosControllerSingleton;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the adf 1 radio frequency in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class RadiosAdf1FreqGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosAdf1FreqGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();
        RadioTextField adf1Text = controller.getAdf1Text();

        String raw = values.get(0);
        if (!adf1Text.getText().equals(raw)) {
            adf1Text.setText(raw);
            log.trace("updateFromXplane(): [" + command + "] -> adf1 set to " + raw);
        }
    }
}
